package com.tourfood;

import java.util.regex.*;
import java.io.IOException;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		String websiteDomain = "https://yarcheplus.ru";
		String website = websiteDomain + "/catalog/ovoschi-i-frukty-187"; // TODO Получать каждую веб-страницу из https://yarcheplus.ru/
		String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36"; // Если убрать, то yarcheplus.ru будет выдавать «Извините, ваш браузер не поддерживается»
		String CSSselectorWorkzone = "#app-content > div > div:last-of-type > div:nth-of-type(2) > div:first-of-type > div:last-of-type > div:last-of-type > div:last-of-type";
		String CSSselectorPagination = "> div:last-of-type > div:last-of-type > a:has(svg)";
		String CSSSelectorProduct = "> div:first-of-type > div > div"; // Почему нужно с «>»? Без него ничего не работает
		String CSSselectorProductTitle = "> div:nth-of-type(2) > div:nth-of-type(2) > div:first-of-type"; // Его содержимое типа: «Томаты Черри Делтари, 250&nbsp;г», «Чеснок молодой», «Чеснок 3 шт», «Лимоны поштучно, 0,1 - 0,3 кг», «Лайм 1 шт.», «Капуста белокочанная Свежий урожай поштучно, 1,2 - 4,5 кг», «Голубика», «Манго желтое, поштучно, 0,3 - 0,8 кг» (желтое и поштучно нужно будет убирать)
		String CSSselectorProductQuantity = "> div:nth-of-type(2) > div:nth-of-type(1) > div:nth-of-type(2)"; // Содержимое здесь — это граммовка. Формат: «250 г» или «1 кг» или «1 шт.»
		String CSSselectorProductPrice = "> div:nth-of-type(2) > div:nth-of-type(1) > div:nth-of-type(1) > div:first-of-type"; // TODO ВНИМАНИЕ: захватывает только самую дешёвую цену (которая по скидке). В будущем захватывать обе цены для предоставления выбора пользователю. Формат: «169,99 ₽»
		Document doc = null;

		String currWebpage = new String(website);
		String nextPageLink = null;
		do {
			try {
				doc = Jsoup.connect(currWebpage).userAgent(userAgent).get();
			} catch (IOException e) {
				e.printStackTrace(); // TODO не показывать стектрейс, а отображать ошибку, что не удалось связаться с сайтом
			}
			Elements products = doc.select(CSSselectorWorkzone).select(CSSSelectorProduct);

			for (Element everyProduct : products) {
				String title = everyProduct.select(CSSselectorProductTitle).first().text();
				String quantity = everyProduct.select(CSSselectorProductQuantity).first().text();
				String price = everyProduct.select(CSSselectorProductPrice).first().text(); // TODO Цена парсится за килограмм, а не за штуку
				String measureRange = extractMeasureRange(title); // Примерная граммовка одной единицы товара (допустим, вес арбуза)

				Matcher matcher = Pattern.compile("^(.*?),\\\\s*([0-9]+).*$").matcher(title);
				if (matcher.find()) {
					title = matcher.group(1);
				}

				System.out.println(title + "; " + measureRange + "; " + quantity + "; " + price);

				// TODO Обработать то, что каждая из этих переменных может быть ПОЧЕМУ-ТО пустой
			}
			try {
				nextPageLink = doc.select(CSSselectorWorkzone).select(CSSselectorPagination).first().attr("href");
			} catch (NullPointerException e) {
				break;
			}
			currWebpage = websiteDomain + new String(nextPageLink);
		} while (nextPageLink != null);
		// TODO В будущем реализовать пагинацию с помощью нажатия кнопки (`<button>`)
	}

	/**
	 * <p>Из строки «Яблоки, Ред, Делишес, 0,2 - 0,5 кг» возвращает подстроку «0,2 - 0,5 кг»</p>
	 * @param title
	 * @return
	 */
	private static String extractMeasureRange(String title) {
		String measureRange = "";
		Pattern pattern = Pattern.compile("(\\d[\\d,.\\s]*\\s*[кгг]*|\\d[\\d,.\\s]*\\s*[-–—]\\s*\\d[\\d,.\\s]*\\s*[кгг]*)$");
		Matcher matcher = pattern.matcher(title);
		if (matcher.find()) {
			measureRange = matcher.group(1).trim();
		}
		return measureRange;
	}
}
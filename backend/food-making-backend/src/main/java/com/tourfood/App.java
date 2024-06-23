package com.tourfood;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.regex.*;
import java.io.IOException;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App {
	public static void main(String[] args) {
		final String WEBSITE_DOMAIN = "https://yarcheplus.ru";
		final String WEBSITE = WEBSITE_DOMAIN + "/catalog/ovoschi-i-frukty-187"; // TODO Получать каждую веб-страницу из https://yarcheplus.ru/
		final String WEBSITE_USERAGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36"; // Если убрать, то yarcheplus.ru будет выдавать «Извините, ваш браузер не поддерживается»
		final String CSSSELECTOR_WORKZONE = "#app-content > div > div:last-of-type > div:nth-of-type(2) > div:first-of-type > div:last-of-type > div:last-of-type > div:last-of-type";
		final String CSSSELECTOR_CATEGORIES = "#app-content > div > div:last-of-type > div:first-of-type > div > a:has(> picture)";
		final String CSSSELECTOR_PAGINATION = "> div:last-of-type > div:last-of-type > a:has(svg)";
		final String CSSSELECTOR_PRODUCT = "> div:first-of-type > div > div";
		final String CSSSELECTOR_PRODUCT_LINK = "> a"; // Почему нужно с «>»? Без него ничего не работает
		final String CSSSELECTOR_PRODUCT_TITLE = "> div:nth-of-type(2) > div:nth-of-type(2) > div:first-of-type"; // Его содержимое типа: «Томаты Черри Делтари, 250&nbsp;г», «Чеснок молодой», «Чеснок 3 шт», «Лимоны поштучно, 0,1 - 0,3 кг», «Лайм 1 шт.», «Капуста белокочанная Свежий урожай поштучно, 1,2 - 4,5 кг», «Голубика», «Манго желтое, поштучно, 0,3 - 0,8 кг» (желтое и поштучно нужно будет убирать)
		final String CSSSELECTOR_PRODUCT_QUANTITY = "> div:nth-of-type(2) > div:nth-of-type(1) > div:nth-of-type(2)"; // Содержимое здесь — это граммовка. Формат: «250 г» или «1 кг» или «1 шт.»
		final String CSSSELECTOR_PRODUCT_PRICE = "> div:nth-of-type(2) > div:nth-of-type(1) > div:nth-of-type(1) > div:first-of-type"; // TODO ВНИМАНИЕ: захватывает только самую дешёвую цену (которая по скидке). В будущем захватывать обе цены для предоставления выбора пользователю. Формат: «169,99 ₽»
		final String CATEGORIES_EXCLUSIONS = "https://yarcheplus.ru/catalog/newest-732 https://yarcheplus.ru/catalog/bestseller-731 https://yarcheplus.ru/catalog/detskoe-pitanie-i-gigiena-224 https://yarcheplus.ru/catalog/igrushki-216 https://yarcheplus.ru/catalog/dlya-doma-223 https://yarcheplus.ru/catalog/krasota-i-zdorovye-220 https://yarcheplus.ru/catalog/zootovary-219 https://yarcheplus.ru/catalog/kolgotki-i-noski-173 https://yarcheplus.ru/catalog/podarochnye-pakety-830 https://yarcheplus.ru/catalog/melochi-u-kassy-762"; // TODO В будущем сделать белый список категорий в виде диапазона

		Document doc = null;
		String currWebpage = new String(WEBSITE);
		String nextPageLink = null;

		List<String> categoriesExclusionsList = new ArrayList<>();
		String[] items = CATEGORIES_EXCLUSIONS.split(" ");
		categoriesExclusionsList.addAll(Arrays.asList(items));

		// TODO Убрать шаблонный код (для парсинга первых продуктов устанавливается ДВА последовательных подключения)
		try {
			doc = Jsoup.connect(currWebpage).userAgent(WEBSITE_USERAGENT).get();
		} catch (IOException e) {
			System.err.println("Не удалось связаться с веб-страницей «" + currWebpage + "».");
			e.printStackTrace();
			System.exit(1);
		}
		Elements categories = doc.select(CSSSELECTOR_CATEGORIES);
		List<String> categoriesList = new ArrayList<>();
		for (Element category : categories) {
			String categoryLink = category.attr("href");
			categoriesList.add(WEBSITE_DOMAIN + categoryLink);
		}

		// Исключение из одного списка другой
		categoriesList.removeAll(categoriesExclusionsList);
		// TODO В будущем оптимизировать (мб можно и без буферного списка)

		for (String categoryLink : categoriesList) {
			currWebpage = categoryLink;
			do {
				try {
					doc = Jsoup.connect(currWebpage).userAgent(WEBSITE_USERAGENT).get();
				} catch (IOException e) {
					System.err.println("Не удалось связаться с веб-страницей «" + currWebpage + "».");
					e.printStackTrace();
					System.exit(1);
				}
				Elements products = doc.select(CSSSELECTOR_WORKZONE).select(CSSSELECTOR_PRODUCT);

				for (Element everyProduct : products) {
					String link = WEBSITE_DOMAIN + everyProduct.select(CSSSELECTOR_PRODUCT_LINK).first().attr("href");
					String title = everyProduct.select(CSSSELECTOR_PRODUCT_TITLE).first().text();
					String quantity = everyProduct.select(CSSSELECTOR_PRODUCT_QUANTITY).first().text();
					String price = everyProduct.select(CSSSELECTOR_PRODUCT_PRICE).first().text(); // TODO Цена парсится за килограмм, а не за штуку
					String[] titleArray = extractMeasureRange(title); // Примерная граммовка одной единицы товара (допустим, вес арбуза)
					String name = titleArray[0];
					String measureRange = titleArray[1];

					if (price.endsWith("₽")) {
						price = price.substring(0, price.length() - 1).trim();
					}
					if (price.endsWith(" ")) {
						price = price.substring(0, price.length() - 1).trim();
					}

					Matcher matcher = Pattern.compile("^(.*?),\\\\s*([0-9]+).*$").matcher(title);
					if (matcher.find()) {
						title = matcher.group(1);
					}

					System.out.println(name + "; " + measureRange + "; " + quantity + "; " + price + "; " + link);

					// TODO Обработать то, что каждая из этих переменных может быть ПОЧЕМУ-ТО пустой
				}
				try {
					nextPageLink = doc.select(CSSSELECTOR_WORKZONE).select(CSSSELECTOR_PAGINATION).first().attr("href");
				} catch (NullPointerException e) {
					break;
				}
				currWebpage = WEBSITE_DOMAIN + new String(nextPageLink);
			} while (nextPageLink != null);
			// TODO В будущем реализовать пагинацию с помощью нажатия кнопки (`<button>`)
		}
	}

	/**
	 * <p>Из строки «Яблоки, Ред, Делишес, 0,2 - 0,5 кг» возвращает подстроку «0,2 - 0,5 кг»</p>
	 * @param title
	 * @return
	 */
	private static String[] extractMeasureRange(String title) {
		String[] measureRange = new String[2];
		Pattern pattern = Pattern.compile("(\\d[\\d,.\\s]*\\s*[кгг]*|\\d[\\d,.\\s]*\\s*[-–—]\\s*\\d[\\d,.\\s]*\\s*[кгг]*)$");
		Matcher matcher = pattern.matcher(title);
		if (matcher.find()) {
			measureRange[1] = matcher.group(1).trim(); // Используем только одну группу
			measureRange[0] = title.substring(0, matcher.start()).trim(); // Название товара до найденного диапазона
		} else {
			measureRange[0] = title.trim(); // Если не найдено, возвращаем всю строку как первую часть
			measureRange[1] = ""; // Вторая часть будет пустой
		}

		if (measureRange[0].endsWith(",")) {
			measureRange[0] = measureRange[0].substring(0, measureRange[0].length() - 1).trim();
		}

		return measureRange;
	}
}
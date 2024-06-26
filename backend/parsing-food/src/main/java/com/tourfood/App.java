package com.tourfood;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.regex.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
	static class Category {
		String link;
		String name;

		Category(String link, String name) {
			this.link = link;
			this.name = name;
		}

		@Override
		public String toString() {
			return "Category{" + "link='" + link + '\'' + ", name='" + name + '\'' + '}';
		}
	}

	public static void main(String[] args) {
		// TODO Позаботиться про безопасность кода: сменить public на private где нужно, поставить static где нужно, сделать геттеры и сеттеры и т.д.
		// Файл слишком большой, вынести подкоманды в отдельные классы
		if (args.length != 0 && args[0].equals("recipes")) {
			final String[][] WEBSITE_MEALTIMEURLS = {
					{"Завтрак", "15", "https://1000.menu/catalog/edim-na-prirode?str=&arr_catalog[188]=188"},
					{"Обед", "15", "https://1000.menu/catalog/edim-na-prirode?str=&arr_catalog[12]=12"},
					{"Ужин", "15", "https://1000.menu/catalog/edim-na-prirode?str=&arr_catalog[819]=819"}
			};
			final String WEBSITE_DOMAIN = "https://1000.menu";
			final String WEBSITE_USERAGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36";
			final String OUTPUT_JSON_FILEPATH = "out/recipes.json";
			// При изменении CSS-селекторов учитывать обфускацию DOM
			final String CSSSELECTOR_WEBSITE_RECIPES = ".cooking-block > .cn-item:not(.ads_enabled)";
			final String CSSSELECTOR_WEBSITE_RECIPENAME = "a.h5";
			final String CSSSELECTOR_WEBSITE_RECIPELINK = "a.h5";
			final String CSSSELECTOR_RECIPE_INGREDIENTS = "#recept-list > .ingredient";
			final String CSSSELECTOR_RECIPE_INGREDIENT_NAME = ".name";
			final String CSSSELECTOR_RECIPE_INGREDIENT_QUANTITY = ".value";
			final String CSSSELECTOR_RECIPE_INGREDIENT_MEASURE = "select.recalc_s_num > option[selected]";
			final String CSSSELECTOR_RECIPE_INGREDIENT_SERVINGS = "#yield_num_input";
			final String CSSSELECTOR_RECIPE_INGREDIENT_SERVINGS_ATTR = "value";

			System.out.println("Парсинг рецептов...");
			System.out.println( "[" +
					WEBSITE_MEALTIMEURLS[0][0] + ": " + WEBSITE_MEALTIMEURLS[0][1] + "; " + 
					WEBSITE_MEALTIMEURLS[1][0] + ": " + WEBSITE_MEALTIMEURLS[1][1] + "; " + 
					WEBSITE_MEALTIMEURLS[2][0] + ": " + WEBSITE_MEALTIMEURLS[2][1] + 
					"]"	);
			System.out.println();

			ObjectMapper objectMapper = new ObjectMapper();
			JsonFactory jsonFactory = new JsonFactory(objectMapper);
			File file = new File(OUTPUT_JSON_FILEPATH);
			if (file.getParentFile() != null) {
			    file.getParentFile().mkdirs();
			}
			
			try ( FileWriter fileWriter = new FileWriter(file);
					JsonGenerator jsonGenerator = jsonFactory.createGenerator(fileWriter))
			{
				for (byte i = 0; i < WEBSITE_MEALTIMEURLS.length; i++) {
					String recipesMealtime = WEBSITE_MEALTIMEURLS[i][0];
					String recipesQuantity = WEBSITE_MEALTIMEURLS[i][1];
					String url = WEBSITE_MEALTIMEURLS[i][2];

					Document doc = null;
					try {
						doc = Jsoup.connect(url).userAgent(WEBSITE_USERAGENT).get();
					} catch (IOException e) {
						System.err.println("Не удалось связаться с веб-страницей «" + url + "».");
						e.printStackTrace();
						System.exit(1);
					}

					System.out.println("Рецепты на " + recipesMealtime + "...");
					System.out.println();

					jsonGenerator.useDefaultPrettyPrinter();
					jsonGenerator.writeStartArray();

					Elements recipes = doc.select(CSSSELECTOR_WEBSITE_RECIPES);
					List<Element> recipesList = recipes.subList(0, Math.min(recipes.size(), Short.valueOf(recipesQuantity)));
					for (Element recipe : recipesList) {
						String recipeName = recipe.select(CSSSELECTOR_WEBSITE_RECIPENAME).first().text();
						String recipeLink = recipe.select(CSSSELECTOR_WEBSITE_RECIPELINK).first().attr("href");

						if (recipeLink != null) {
							recipeLink = WEBSITE_DOMAIN + recipeLink;

							Document recipeWebpage = null;
							try {
								recipeWebpage = Jsoup.connect(recipeLink).userAgent(WEBSITE_USERAGENT).get();
							} catch (IOException e) {
								System.err.println("Не удалось связаться с веб-страницей «" + recipeLink + "».");
								e.printStackTrace();
								System.exit(1);
							}

							int recipeServings = Short.parseShort(recipeWebpage.select(CSSSELECTOR_RECIPE_INGREDIENT_SERVINGS).first().attr(CSSSELECTOR_RECIPE_INGREDIENT_SERVINGS_ATTR));

							System.out.println(recipeName);
							System.out.println("Ингредиенты на 1 порцию:");

							Elements ingredients = recipeWebpage.select(CSSSELECTOR_RECIPE_INGREDIENTS);
							for (Element ingredient : ingredients) {
								boolean ingredientRequired = true;
								float ingredientQuantity = 0f;
								String ingredientMeasure = null;
								String ingredientName = null;

								try {
									ingredientName = ingredient.select(CSSSELECTOR_RECIPE_INGREDIENT_NAME).first().text();
								} catch (NullPointerException e) {
									e.printStackTrace();
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								}
								try {
									ingredientMeasure = ingredient.select(CSSSELECTOR_RECIPE_INGREDIENT_MEASURE).first().text();
								} catch (NullPointerException e) {
									ingredientRequired = false;
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								}

								if (ingredientRequired == true) {
									ingredientQuantity = Float.parseFloat(ingredient.select(CSSSELECTOR_RECIPE_INGREDIENT_QUANTITY).first().text());
								}

								String[] ingredientQuantityConverted = new String[2];
								if (ingredientRequired == true) {
									ingredientQuantityConverted = measureConvert(ingredientQuantity, ingredientMeasure);
									ingredientQuantity = Float.parseFloat(ingredientQuantityConverted[0]);
									ingredientMeasure = ingredientQuantityConverted[1];
								}

								float ingredientQuantityPerServing = ingredientQuantity / recipeServings;
								BigDecimal ingredientQuantityPerServingBD = roundIngredientQuantity(ingredientQuantityPerServing);
								String ingredientQuantityString = ingredientQuantityPerServingBD.toPlainString();

								// Начать новый рецепт, если это первый ингредиент.
								if (ingredient == ingredients.get(0)) {
									jsonGenerator.writeStartObject();
									jsonGenerator.writeStringField("mealtime", recipesMealtime);
									jsonGenerator.writeStringField("name", recipeName);
									jsonGenerator.writeFieldName("ingredients");
									jsonGenerator.writeStartArray();
								}

								jsonGenerator.writeStartObject();
								jsonGenerator.writeStringField("ingredient_name", ingredientName);
								if (ingredientRequired == true) {
									jsonGenerator.writeStringField("ingredient_quantity", ingredientQuantityString);
									jsonGenerator.writeStringField("ingredient_measure", ingredientMeasure);
									System.out.println("* " + ingredientName + " — " + ingredientQuantityString + " " + ingredientMeasure);
								} else {
									System.out.println("* " + ingredientName);
								}
								jsonGenerator.writeEndObject();

								// Закрытие массива и объекта рецепта, если это последний ингредиент.
								if (ingredient == ingredients.get(ingredients.size() - 1)) {
									jsonGenerator.writeEndArray();
									jsonGenerator.writeEndObject();
									jsonGenerator.flush();
								}
							}
						}

						if (i < WEBSITE_MEALTIMEURLS.length) {
							System.out.println();
						}
					}

					jsonGenerator.writeEndArray();

					if (i+1 < WEBSITE_MEALTIMEURLS.length) {
						System.out.println("================================================");
						System.out.println();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("Парсинг рецептов завершён.");
		} else if (args.length != 0 && args[0].equals("products")) {
			final String WEBSITE_DOMAIN = "https://yarcheplus.ru";
			final String WEBSITE_USERAGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36";
			final String OUTPUT_JSON_FILEPATH = "out/products.json";
			// При изменении CSS-селекторов учитывать обфускацию DOM
			final String CSSSELECTOR_WORKZONE_PRODUCTS = "#app-content > div > div:last-of-type > div:nth-of-type(2) > div:first-of-type > div:last-of-type > div:last-of-type > div:last-of-type";
			final String CSSSELECTOR_WORKZONE_PRODUCTDIALOG = "main#app > div:not(#app-content) > div > div[role=\"dialog\"] div:has(> div > h1)";
			final String CSSSELECTOR_CATEGORIES = "#app-content > div > div:last-of-type > div:first-of-type > div > a:has(> picture)";
			final String CSSSELECTOR_PAGINATION = "> div:last-of-type > div:last-of-type > a:has(svg)";
			final String CSSSELECTOR_PRODUCT = "> div:first-of-type > div > div";
			final String CSSSELECTOR_PRODUCT_LINK = "> a";
			final String CSSSELECTOR_PRODUCT_TITLE = "> div:nth-of-type(2) > div:nth-of-type(2) > div:first-of-type";
			final String CSSSELECTOR_PRODUCT_QUANTITY = "> div:nth-of-type(2) > div:nth-of-type(1) > div:nth-of-type(2)";
			final String CSSSELECTOR_PRODUCT_PRICE = "> div:nth-of-type(2) > div:nth-of-type(1) > div:nth-of-type(1) > div:first-of-type";
			final String CSSSELECTOR_CATEGORY_NAME = "a > div";
			final String CATEGORIES_EXCLUSIONS = "https://yarcheplus.ru/catalog/newest-732 https://yarcheplus.ru/catalog/bestseller-731 https://yarcheplus.ru/catalog/detskoe-pitanie-i-gigiena-224 https://yarcheplus.ru/catalog/igrushki-216 https://yarcheplus.ru/catalog/dlya-doma-223 https://yarcheplus.ru/catalog/krasota-i-zdorovye-220 https://yarcheplus.ru/catalog/zootovary-219 https://yarcheplus.ru/catalog/kolgotki-i-noski-173 https://yarcheplus.ru/catalog/podarochnye-pakety-830 https://yarcheplus.ru/catalog/melochi-u-kassy-762";
			final String DELIM = ", ";

			System.out.println("Парсинг продуктов...");
			System.out.println();

			Document doc = null;
			String currWebpage = new String(WEBSITE_DOMAIN);
			String nextPageLink = null;

			List<String> categoriesExclusionsList = new ArrayList<>();
			String[] items = CATEGORIES_EXCLUSIONS.split(" ");
			categoriesExclusionsList.addAll(Arrays.asList(items));

			try {
				doc = Jsoup.connect(currWebpage).userAgent(WEBSITE_USERAGENT).get();
			} catch (IOException e) {
				System.err.println("Не удалось связаться с веб-страницей «" + currWebpage + "».");
				e.printStackTrace();
				System.exit(1);
			}
			System.out.println("Подключение к веб-странице «" + currWebpage + "» успешно установлено.");
			Elements categories = doc.select(CSSSELECTOR_CATEGORIES);
			List<Category> categoriesList = new ArrayList<>();
			for (Element category : categories) {
				String categoryLink = WEBSITE_DOMAIN + category.attr("href");
				String categoryName = category.select(CSSSELECTOR_CATEGORY_NAME).text();
				categoriesList.add(new Category(categoryLink, categoryName));
			}

			categoriesList.removeIf(category -> categoriesExclusionsList.contains(category.link));

			ObjectMapper objectMapper = new ObjectMapper();
			JsonFactory jsonFactory = new JsonFactory(objectMapper);
			File file = new File(OUTPUT_JSON_FILEPATH);
			if (file.getParentFile() != null) {
			    file.getParentFile().mkdirs();
			}

			try (FileWriter fileWriter = new FileWriter(file);
					JsonGenerator jsonGenerator = jsonFactory.createGenerator(fileWriter))
			{
				jsonGenerator.useDefaultPrettyPrinter();
				jsonGenerator.writeStartArray();
				System.out.println("Путь к JSON-файлу: «" + OUTPUT_JSON_FILEPATH + "». Завершающее `]` появится только после окончания сбора данных с сайта.");
				System.out.println("================================================================================");
				System.out.println("Имя категории" + DELIM + "название продукта" + DELIM + "величина и диапазон" + DELIM + "количество" + DELIM + "стоимость (в рублях)" + DELIM + "ссылка на продукт в магазине" + DELIM + "кол-во белков" + DELIM + "кол-во жиров" + DELIM + "колв-о углеводов" + DELIM + "кол-во килокалорий");

				for (Category category : categoriesList) {
					currWebpage = category.link;
					do {
						try {
							doc = Jsoup.connect(currWebpage).userAgent(WEBSITE_USERAGENT).get();
						} catch (IOException e) {
							System.err.println("Не удалось связаться с веб-страницей «" + currWebpage + "».");
							e.printStackTrace();
							System.exit(1);
						}
						Elements products = doc.select(CSSSELECTOR_WORKZONE_PRODUCTS).select(CSSSELECTOR_PRODUCT);

						for (Element everyProduct : products) {
							String link = WEBSITE_DOMAIN + everyProduct.select(CSSSELECTOR_PRODUCT_LINK).first().attr("href");
							String title = everyProduct.select(CSSSELECTOR_PRODUCT_TITLE).first().text();
							String quantity = everyProduct.select(CSSSELECTOR_PRODUCT_QUANTITY).first().text();
							String price = everyProduct.select(CSSSELECTOR_PRODUCT_PRICE).first().text(); // TODO Цена парсится за килограмм, а не за штуку
							String[] titleArray = extractMeasureRange(title);
							String name = titleArray[0];
							String measureRange = titleArray[1];

							Document docProductDialog = null;
							try {
								docProductDialog = Jsoup.connect(link).userAgent(WEBSITE_USERAGENT).get();
							} catch (IOException e) {
								System.err.println("Не удалось связаться с веб-страницей «" + link + "».");
								e.printStackTrace();
								System.exit(1);
							}
							Elements productDialog = docProductDialog.select(CSSSELECTOR_WORKZONE_PRODUCTDIALOG + "> div");
							byte i = 0;
							Element productFoodEnergy = null;
							for (Element productPropertyDiv : productDialog) {
								Element foo = productPropertyDiv.select("a[href$='/reviews']").first();
								// if (foo == null) {
								// 	foo = productPropertyDiv.select("> div > div > div > div").first(); // .first().text().equalsIgnoreCase("Нет оценок")
								// }
								i++;
								if (foo != null) {
									productFoodEnergy = productDialog.get(i - 2); // -1 т.к. отсчёт с нуля и -1 т.к. нужно взять более ранний элемент
								}
							}

							String proteins = null;
							String fats = null;
							String carbonates = null;
							String kilocal = null;
							try {
								proteins = searchDivs(productFoodEnergy, "Белки");
							} catch (NullPointerException e) {
								proteins = "-";
							}
							try {
								fats = searchDivs(productFoodEnergy, "Жиры");
							} catch (NullPointerException e) {
								fats = "-";
							}
							try {
								carbonates = searchDivs(productFoodEnergy, "Углеводы");
							} catch (NullPointerException e) {
								carbonates = "-";
							}
							try {
								kilocal = searchDivs(productFoodEnergy, "ккал");
							} catch (NullPointerException e) {
								kilocal = "-";
							}

							if (price.endsWith("₽")) {
								price = price.substring(0, price.length() - 1).trim();
							}
							price = price.trim();
							price = price.replaceAll(",", ".");

							Matcher matcher = Pattern.compile("^(.*?),\\\\s*([0-9]+).*$").matcher(title);
							if (matcher.find()) {
								title = matcher.group(1);
							}

							jsonGenerator.writeStartObject();
							jsonGenerator.writeStringField("category", category.name);
							jsonGenerator.writeStringField("name", name);
							jsonGenerator.writeStringField("measureRange", measureRange);
							jsonGenerator.writeStringField("quantity", quantity);
							jsonGenerator.writeStringField("price", price);
							jsonGenerator.writeStringField("link", link);
							jsonGenerator.writeStringField("proteins", proteins);
							jsonGenerator.writeStringField("fats", fats);
							jsonGenerator.writeStringField("carbonates", carbonates);
							jsonGenerator.writeStringField("kilocalories", kilocal);
							jsonGenerator.writeEndObject();
							jsonGenerator.flush();

							System.out.println(category.name + DELIM + name + DELIM + measureRange + DELIM + quantity + DELIM + price + DELIM + link + DELIM + proteins + DELIM + fats + DELIM + carbonates + DELIM + kilocal);
						}
						try {
							nextPageLink = doc.select(CSSSELECTOR_WORKZONE_PRODUCTS).select(CSSSELECTOR_PAGINATION).first().attr("href");
						} catch (NullPointerException e) {
							break;
						}
						currWebpage = WEBSITE_DOMAIN + new String(nextPageLink);
					} while (nextPageLink != null);
				}

				jsonGenerator.writeEndArray();
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println();
			System.out.println("Парсинг продуктов завершён.");
		} else {
			System.err.println("Необходимо ввести подкоманду!");
			System.err.println("Подкоманды: recipes — парсинг рецептов; products — парсинг продуктов.");
			System.exit(1);
		}
	}

	/**
	 * <p>Возвращает ориентировочную граммовку одной единицы товара (допустим, вес арбуза).
	 * Из строки `Яблоки, Ред, Делишес, 0,2 - 0,5 кг` возвращает подстроку `0,2 - 0,5 кг`</p>
	 * Реальные примеры входных данных:
	 * `Голубика`
	 * `Чеснок молодой`
	 * `Чеснок 3 шт`
	 * `Томаты Черри Делтари, 250 г`
	 * `Лимоны поштучно, 0,1 - 0,3 кг`
	 * `Капуста белокочанная Свежий урожай поштучно, 1,2 - 4,5 кг`
	 * `Манго желтое, поштучно, 0,3 - 0,8 кг`
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

	/**
	 * <p>Рекурсивный поиск необходимых БЖУ и калорий</p>
	 * @param element
	 * @param searchText
	 */
	private static String searchDivs(Element element, String searchText) {
		if (element.tagName().equals("div") && element.text().equals(searchText)) {
			// Находим предыдущий sibling элемент с тегом div
			Element previousSibling = element.previousElementSibling();
			while (previousSibling != null && !previousSibling.tagName().equalsIgnoreCase("div")) {
				previousSibling = previousSibling.previousElementSibling();
			}
			return previousSibling != null ? previousSibling.text() : null;
		}

		// Рекурсивно обходим дочерние элементы
		Elements children = element.children();
		for (Element child : children) {
			String result = searchDivs(child, searchText);
			if (result != null) {
				return result;
			}
		}

		return null;
	}

	/**
	 * <p>Конвертация в стандартные меры системы СИ
	 * Если 2-ым аргументом подаётся «шт.» и др. мера, то она остаётся неизменным
	 * @param ingredientQuantity
	 * @param ingredientMeasure
	 * @return
	 * @throws IllegalArgumentException
	 */
	private static String[] measureConvert(float ingredientQuantity, String ingredientMeasure) {
		String[] ingredientMeasureConverted = {String.valueOf(ingredientQuantity), ingredientMeasure};

		if (ingredientMeasureConverted[1] != null) {
			if (ingredientMeasureConverted[1] != "") {
				switch (ingredientMeasureConverted[1].toLowerCase()) {
				case "кг":
					ingredientMeasureConverted[0] = String.valueOf(ingredientQuantity *= 1000);
					ingredientMeasureConverted[1] = "г";
					break;
				case "л":
					ingredientMeasureConverted[0] = String.valueOf(ingredientQuantity *= 1000);
					ingredientMeasureConverted[1] = "мл";
					break;
				case "чайн.л.":
					ingredientMeasureConverted[0] = String.valueOf(ingredientQuantity *= 5);
					ingredientMeasureConverted[1] = "мл";
					break;
				case "десерт.л.":
					ingredientMeasureConverted[0] = String.valueOf(ingredientQuantity *= 10);
					ingredientMeasureConverted[1] = "мл";
					break;
				case "стол.л.":
					ingredientMeasureConverted[0] = String.valueOf(ingredientQuantity *= 15);
					ingredientMeasureConverted[1] = "мл";
					break;
				case "стак.":
					ingredientMeasureConverted[0] = String.valueOf(ingredientQuantity *= 200);
					ingredientMeasureConverted[1] = "мл";
					break;
				case "зубч.":
				case "листк.":
					ingredientMeasureConverted[1] = "шт.";
					break;
				}
			}
		}

		return ingredientMeasureConverted;
	}

	/**
	 * <p>«0.000033333334» → «0.00003» — если дробная часть начинается с нуля, у этой дробной части оставлять первую ненулевую цифру;
	 * «0.3333334» → «0.33» — если дробная часть начинается с ненулевой цифры, число округляется до двух цифр после разделителя целой и дробной части («до двух знаков после запятой»);
	 * «135.12» → «135» — если целая часть больше 100, число округляется до целого;
	 * «0.20» → «0.2»;
	 * «10.0» → «10».</p>
	 * @param quantity
	 * @return
	 */
	private static BigDecimal roundIngredientQuantity(float quantity) {
		BigDecimal bdQuantity = BigDecimal.valueOf(quantity);
		int intPart = bdQuantity.intValue();
		BigDecimal fractionalPart = bdQuantity.remainder(BigDecimal.ONE);

		if (intPart > 100) {
			return bdQuantity.setScale(0, RoundingMode.HALF_UP);
		}

		// Убираем лишние нули в конце дробной части
		BigDecimal trimmedBdQuantity = bdQuantity.stripTrailingZeros();

		// Если целое число, возвращаем его без дробной части
		if (trimmedBdQuantity.scale() <= 0) {
			return trimmedBdQuantity;
		}

		String fractionalPartStrTrimmed = fractionalPart.toPlainString().substring(2); // Убираем "0."
		if (fractionalPartStrTrimmed.startsWith("0")) {
			int firstNonZeroIndex = 1; // Первый символ после 0
			while (firstNonZeroIndex < fractionalPartStrTrimmed.length() && fractionalPartStrTrimmed.charAt(firstNonZeroIndex) == '0') {
				firstNonZeroIndex++;
			}
			if (firstNonZeroIndex < fractionalPartStrTrimmed.length()) {
				return bdQuantity.setScale(firstNonZeroIndex + 1, RoundingMode.HALF_UP).stripTrailingZeros();
			}
			return bdQuantity.stripTrailingZeros();
		} else {
			return bdQuantity.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
		}
	}
}
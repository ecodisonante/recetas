package com.ecodisonante.recetas.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class DataLoader {

    public static List<Author> loadAuthors() {
        return Arrays.asList(
                new Author(1L, "Juan Pérez", "juanperez@example.com", "password1"),
                new Author(2L, "Ana García", "anagarcia@example.com", "password2"),
                new Author(3L, "Carlos Gómez", "carlosgomez@example.com", "password3")
        );
    }

    // Cargar recetas
    public static List<Recipe> loadRecipes() {
        return Arrays.asList(
                new Recipe(1L, "Tacos", "Los tacos son un platillo tradicional mexicano que consiste en una tortilla de maíz o trigo, doblada o enrollada, rellena con diversos ingredientes como carne, pollo, pescado o vegetales. Se acompañan con salsas, cilantro, cebolla y limón, ofreciendo una explosión de sabores típicos de la gastronomía mexicana.", Arrays.asList("Tortillas", "Carne", "Cebolla", "Salsa"), "Cocina la carne, calienta las tortillas y sirve.", 4, "México", 2, "/img/recipe/1.jpg", 1L, LocalDateTime.now(), 4.5),
                new Recipe(2L, "Pizza", "La pizza es una especialidad italiana originaria de Nápoles, que consiste en una base de masa plana cubierta con salsa de tomate, queso mozzarella y una variedad de ingredientes como pepperoni, verduras o mariscos. Se hornea hasta que la masa esté crujiente y el queso fundido, siendo un plato popular en todo el mundo.", Arrays.asList("Harina", "Tomate", "Queso", "Aceite de oliva"), "Amasa la base, añade los ingredientes y hornea.", 2, "Italia", 3, "/img/recipe/2.jpg", 2L, LocalDateTime.now(), 5.0),
                new Recipe(3L, "Sushi", "El sushi es un plato japonés que combina arroz aderezado con vinagre y una variedad de ingredientes como pescado crudo, mariscos y verduras. Se presenta en formas como rollos (maki), bolas (nigiri) o conos (temaki), y es apreciado por su frescura, delicadeza y equilibrio de sabores.", Arrays.asList("Arroz", "Pescado", "Alga nori", "Salsa de soja"), "Prepara el arroz, añade el pescado y enrolla.", 4, "Japón", 4, "/img/recipe/3.jpg", 3L, LocalDateTime.now(), 4.0),
                new Recipe(4L, "Enchiladas", "Las enchiladas son una preparación mexicana que consiste en tortillas de maíz rellenas de pollo, carne o queso, enrolladas y bañadas en salsa de chile roja o verde. Se suelen cubrir con queso derretido y crema, y se acompañan con lechuga, cebolla y aguacate, combinando sabores picantes y cremosos.", Arrays.asList("Tortillas", "Pollo", "Queso", "Salsa roja"), "Rellena las tortillas, añade la salsa y hornea.", 4, "México", 3, "/img/recipe/4.jpg", 1L, LocalDateTime.now(), 4.3),
                new Recipe(5L, "Paella", "La paella es un plato tradicional español originario de Valencia, elaborado a base de arroz cocido en una paellera con ingredientes como mariscos, pollo, conejo y verduras. Sazonada con azafrán que le da su característico color dorado, es un símbolo de la gastronomía española y se disfruta en reuniones familiares y festivales.", Arrays.asList("Arroz", "Mariscos", "Azafrán", "Aceite de oliva"), "Cocina el arroz con los mariscos y el azafrán.", 6, "España", 4, "/img/recipe/5.jpg", 2L, LocalDateTime.now(), 4.7),
                new Recipe(6L, "Ramen", "El ramen es una sopa japonesa que contiene fideos de trigo servidos en un caldo sabroso, a menudo elaborado con carne, huesos de cerdo o pollo, y sazonado con miso o salsa de soja. Se acompaña con ingredientes como rebanadas de cerdo, huevo cocido, algas y cebollín, ofreciendo una comida reconfortante y llena de sabor.", Arrays.asList("Fideos", "Caldo de cerdo", "Huevo", "Cebolla verde"), "Hierve el caldo, añade los fideos y los toppings.", 2, "Japón", 3, "/img/recipe/6.jpg", 3L, LocalDateTime.now(), 4.8),
                new Recipe(7L, "Hamburguesa", "La hamburguesa es un sándwich que consiste en un pan redondo que contiene una o más porciones de carne molida de res, cocinada a la parrilla o a la plancha. Se acompaña con ingredientes como queso, lechuga, tomate, cebolla, pepinillos y salsas, siendo un icono de la comida rápida y popular en todo el mundo.", Arrays.asList("Pan de hamburguesa", "Carne", "Queso", "Lechuga", "Tomate"), "Asa la carne, monta la hamburguesa con los ingredientes.", 1, "Estados Unidos", 2, "/img/recipe/7.jpg", 1L, LocalDateTime.now(), 4.2),
                new Recipe(8L, "Ceviche", "El ceviche es un plato típico de varios países latinoamericanos, especialmente de Perú, que consiste en pescado o mariscos crudos marinados en jugo de limón o lima. Se sazona con ají, cebolla y cilantro, y se sirve frío, destacando por su frescura y sabores cítricos.", Arrays.asList("Pescado", "Limón", "Cebolla", "Cilantro"), "Marina el pescado con limón, mezcla con los ingredientes.", 4, "Perú", 2, "/img/recipe/8.jpg", 2L, LocalDateTime.now(), 4.6),
                new Recipe(9L, "Falafel", "El falafel es una especialidad del Medio Oriente hecha de garbanzos o habas molidos mezclados con especias y hierbas, formando bolas o croquetas que se fríen hasta quedar crujientes. Se suele servir en pan de pita con ensalada y salsas como tahini o yogur, siendo una opción popular vegetariana.", Arrays.asList("Garbanzos", "Cebolla", "Ajo", "Perejil"), "Mezcla los ingredientes y fríe las bolitas de falafel.", 3, "Líbano", 3, "/img/recipe/9.jpg", 3L, LocalDateTime.now(), 4.4),
                new Recipe(10L, "Lasagna", "La lasaña es un plato italiano que consiste en láminas de pasta intercaladas con capas de carne molida, salsa de tomate, bechamel y queso, generalmente mozzarella y parmesano. Se hornea hasta que esté dorada y burbujeante, ofreciendo una combinación rica y reconfortante de sabores y texturas.", Arrays.asList("Pasta", "Carne", "Salsa de tomate", "Queso"), "Monta las capas de pasta, carne y salsa, hornea.", 4, "Italia", 4, "/img/recipes10.jpg", 1L, LocalDateTime.now(), 4.9)
        );
    }

}
















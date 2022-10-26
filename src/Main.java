import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.stream.Collectors;

public class Main {

    public static ICalculadora calculadora(){
        return (a,b) -> {return a+b;};
    }

    public static void main(String[] args) {

        System.out.println("###################");
        System.out.println("### Curso Java8 ###");
        System.out.println("###################\n");

        //### Functional Interfaces ###
        //Las interfaces funcionales tienen un único método abstracto. Se le añade @FunctionalInterface a la interfaz
        //Default Method
        System.out.println("### Functional Interfaces ###");
        IUtils utils=new Utils();
        utils.sayNumbers(Arrays.asList(1,2,3,4));
        //Static Method
        System.out.println("Suma: "+IUtils.suma(Arrays.asList(1,2,3,4)));
        System.out.println("\n");

        //### Lambda ###
        //Las expresiones Lambda se pueden utilizar sobre interfaces funcionales
        //La operación se realiza sobre el único método abstract que tiene esta interfaz
        //La parte izquierda son los parámetros de ese método y la derecha su implementación (LambdaParameters -> LambdaBody)
        System.out.println("### Lambda ###");

        IUtils util = (texto) -> System.out.println(texto);
        util.print("Hola mundo, soy lambda");
        
        System.out.println("Suma con Lambda: "+calculadora().suma(2, 3));
        //Ordenación de nombres según su longitud
        List<String> nombres = Arrays.asList("Juan", "Antonia", "Pedro");
		
        //PRE-JAVA8
		Comparator<String> comparadorLongitud = new Comparator<String>(){
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        };
		Collections.sort(nombres,comparadorLongitud);
		System.out.println("Ordenación PRE-JAVA8: "+nombres);

        //JAVA8
        Comparator<String> comparadorLongitudJava8 = (o1, o2) -> o1.length() - o2.length();
		Collections.sort(nombres,comparadorLongitudJava8);
				
		System.out.println("Ordenación JAVA8: "+nombres);

        //JAVA8
        Collections.sort(nombres,Comparator.comparing(String::length));
				
		System.out.println("Ordenación JAVA8: "+nombres);
        System.out.println("\n");

        //JAVA Util Funtions
        //Function <Persona, String> nombre = per -> per.getNombre(); Le las la persona, devuelve un string
        //andThen: nombre=nombre.andThen(it ->it.toUpperCase());
        //Unary (IntUnaryOperator, LongUnaryOperator, DoubleUnaryOperator) para los tipos primitivos
        //Interface Function <T,R>
        //f(primitivo)->R : IntFunction<R>
        //f(T)->primitivo : ToLongFunction<T>
        //f(primitivo)->primitivo : DoubleToIntFunction
        //Consumer<T> (Acepta un parametro, pero no produce resultado): Consumer<String> impresor = (it) -> {System.oyt.println(it);} 
        //Supplier<T> (Operación sin ningún parámetro, que produce un valor): 
        //Random random= new Random();
        //Supplier<Integer> generador = () -> random.nextInt();
        //Predicate<T> (El resultado de evaluar un objeto, es un valor cierto o falso): Predicate<String> cadenaCorta = it -> it.length<10;
        //El prefijo Binary es para cuando se duplica el número de parámetros:
        //BinaryOperator<T> : f(t,t) -> t (Dos parámetros, que devuelve un tipo igual que los parámetros)
        //BiFunction<T,U,R> : f(t,u) -> r (Parámetros y resultado, todos distintos entre si)
        //BiPredicate<T,U> : f(t,u) -> boolean
        //Ej: DoubleBinaryOperator
        System.out.println("### JAVA Util Funtions ###");
        IntUnaryOperator cuadrado = a -> a * a;
        System.out.println("El cuadrado de 2 es: "+cuadrado.applyAsInt(2));

        LongBinaryOperator numeroMenor = (a,b) -> a < b?a:b;
        System.out.println("El numero menor de 3 y 5 es: "+numeroMenor.applyAsLong(3,5));
        System.out.println("\n");

        //Method references (Clase::MétodoEstático)
        //Si no es un método estático, hay que crear antes una instancia de la clase
        //Se puede referenciar también un constructor: Function<String, Integer> conversor = Integer::new;
        System.out.println("### Method references ###");
        List<String> nombres2 = Arrays.asList("Juan", "Antonia", "Pedro","Raúl","Antonieta");
        nombres2.sort(IUtils::compareLength);
        System.out.println("El orden de nombres 2: "+nombres2);

        Function<String, Integer> conversor = Integer::new;
        int numeroConvertido=conversor.apply("3");
        System.out.println("El número convertido es: "+numeroConvertido);
        System.out.println("\n");

        //Optional
        //Con optional evitamos que se pueda recibir un valor null
        //String saluda (Optional<Persona> persona){...}
        //Creación:
        //Optional<Persona> persona = Optional.of(juan); (Si va a tener valor)
        //Optional<Persona> persona = Optional.empty(); (Si no va a tener null)
        //Optional<Persona> persona = Optional.ofNullable(juan); (Si puede tener valor null)
        System.out.println("### Optional ###");
        Persona josep= new Persona("Josep Enric", "Esteve", "Colomer", 36);
        Optional<Persona> persona = Optional.of(josep);
        Optional<Persona> noPersona = Optional.empty();
        System.out.println(IUtils.saluda4(persona));
        System.out.println(IUtils.saluda4(noPersona));
        System.out.println("\n");
        
        //Streams
        //Agrupación de elementos sobre los que podemos especificar operaciones
        //Creación -> Operaciones intermedias -> Operación terminal (Stream Pipelines)
        //Operaciones intermedias
        //Operaciones de conversión: (map)
        //Operaciones de filtrado: (filter)
        //Operaciones de ordenado: (sorted)
        //Operaciones terminales
        //Operar sobre los elementos para producir un resultado final (reduce) (Ej: .reduce("",(a,b)->a+"\n"+b) (Haría saltos de líneas) )
        //Collectors: Agrupaciones, Operaciones aritméticas (medias, sumas), conversión a Collections (List, Set, Map)
        //Ejemplo, Agrupar por un criterio (Ej: Map<String, List<Alumnos>> alumnos = alumnos.stream().collect(Collectors.groupingBy(Alumno::getEscuela)))  
        //Peek: No afecta a la ejecución del stream, y nos permite ver los que sucede (.peek(System.out::println))
        System.out.println("### Streams ###");
        Persona juan= new Persona("Juan", "García", "López", 25);
        Persona maria= new Persona("María", "Ruiz", "Escobar", 32);
        Persona luis= new Persona("Luis", "Moreno", "Moreno", 23);

        List<Persona> personas = Arrays.asList(maria, juan, josep, luis);
        List<String> nombresPersonas = IUtils.obtenerNombres(personas);
        System.out.println("nombres: "+nombresPersonas);

        Double edadMedia = personas.stream().collect(Collectors.averagingInt(Persona::getEdad));
        System.out.println("edad media: "+edadMedia);

    }

}
import java.security.cert.PKIXRevocationChecker.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

//Esta anotación sirve para indicar que es una interface con un único método abstracto.
//De esta manera sabemos que se puede usar con Lambdas.
@FunctionalInterface
public interface IUtils {
    //Método Abstracto (El de toda la vida)
    public void print(String texto);

    //Método default (Implementa un método en la interface)
    default void sayNumbers(List<Integer> numeros){
        for(Integer num: numeros){
            print(num.toString());
        }
    }

    //Métodos estáticos
    static int suma(List<Integer> numeros){
        int suma=0;
        for(Integer num: numeros){
            suma+=num;
        }
        return suma;
    }

    public static int compareLength(String o1, String o2){
        return o1.length()-o2.length();
    }

    //Con optional
    public static String saluda(Optional<Persona> persona){
        if(persona.isPresent()){
            return "Hola "+persona.get().getNombre();
        }else{
            return "Estoy solo";
        }
    }

    //Con optional y usando map, que devuelve un optional vacío si no tiene valor
    public static Optional<String> saluda2(Optional<Persona> persona){
        return persona.map(it->"Hola "+it.getNombre());
    }

    //Con optional y usando map con else
    public static String saluda3(Optional<Persona> persona){
        return persona.map(it->"Hola "+it.getNombre())
                      .orElse("Estoy solo");
    }

    //Con optional y usando map con else, y method references
    public static String saluda4(Optional<Persona> persona){
        return persona.map(Persona::getNombre)
                      .map("Hola "::concat)
                      .orElse("Estoy solo");
    }

    //Streams
    public static List<String> obtenerNombres(List<Persona> personas){
        return personas.stream()
                        .map(p->p.getNombre())
                        .sorted()
                        .collect(Collectors.toList());
    }

}

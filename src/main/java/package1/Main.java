package package1;


import java.util.function.Predicate;

public class Main {

    public static void question_1_1() {

        Somme<Integer> sommeInteger = new Somme<Integer>() {
            @Override
            public Integer somme(Integer nb1, Integer nb2) {
                return nb1 + nb2;
            }
        };
        Somme<Double> sommeDouble = new Somme<Double>() {
            @Override
            public Double somme(Double nb1, Double nb2) {
                return nb1 + nb2;
            }
        };
        Somme<Long> sommeLong = new Somme<Long>() {
            @Override
            public Long somme(Long nb1, Long nb2) {
                return nb1 + nb2;
            }
        };

        Somme<String> sommeString = new Somme<String>() {
            @Override
            public String somme(String nb1, String nb2) {
                return nb1 + nb2;
            }
        };
        String a = "a";
        String b = "b";

        System.out.println(sommeInteger.somme(1, 2));
        System.out.println(sommeDouble.somme(1.0, 2.2));
        System.out.println(sommeLong.somme(10000L, 10000L));
        System.out.println(sommeString.somme(a, b));


    }

    public static void question_2_1() {

    Paire <Integer,Integer> patrick = new Paire<>(120,170);
    Paire <Integer,Integer> bob = new Paire<>(80,110);


    Predicate <Paire<Integer,Integer>> tropPetit = x-> x.fst <100;
    tropPetit.test(bob);

    Predicate <Paire<Integer,Integer>> tropGrand = x-> x.fst >200;
    tropGrand.test(patrick);

    Predicate <Paire<Integer,Integer>> tropLourd = x-> x.snd >150;
    tropLourd.test(patrick);

    Predicate <Paire<Integer,Integer>> tailleIncorrecte = tropGrand.or(tropPetit);
    tailleIncorrecte.test(patrick);

    Predicate <Paire<Integer,Integer>> tailleCorrecte = tropGrand.negate().and(tropPetit.negate());
    tailleCorrecte.test(bob);

    Predicate <Paire<Integer,Integer>> poidsCorrecte = tropLourd.negate();
    poidsCorrecte.test(bob);

    Predicate <Paire<Integer,Integer>> accesAutorise = tailleCorrecte.and(poidsCorrecte);
    accesAutorise.test(bob);



    }

    public static void question_2_(){
        //

    }


}



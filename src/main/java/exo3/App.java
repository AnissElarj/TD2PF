package exo3;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class App {



    public static void afficheSi(String entete, Predicate <Etudiant> condition, Annee annee) {

        System.out.println(entete);

        for (Etudiant etudiant : annee.etudiants()) {
            if (condition.test(etudiant)){

                System.out.println("\n" + etudiant);

            }
        }

    }

    public static void afficheSi2(String entete, Predicate <Etudiant> condition, Annee annee){
        //foreach
        System.out.println(entete);

        annee.etudiants().forEach(etudiant -> {
            if (condition.test(etudiant)) System.out.println(etudiant) ;
        });
    }

    public static final Set<Matiere>  toutesLesMatieresDeLAnnee(Annee a){
        Set<Matiere> rtr = new HashSet<>();
        for(UE ue : a.ues()){
            rtr.addAll(ue.ects().keySet());
        }
        return rtr;

    }

    public static final Set <Matiere>  toutesLesNotesDeLAnnee(Annee a){
        Set<Matiere> rtr = new HashSet<>();
        for(UE ue : a.ues()){
            rtr.addAll(ue.ects().keySet());
        }
        return rtr;

    }

    public static final Double moyenne (Etudiant etudiant, Boolean condition) {
        Double moyenne = 0.0;
        Integer coefTotal = 0;

        if (condition) { return null;}
        else{
            for (UE ue : etudiant.annee().ues()) {

                for (Map.Entry<Matiere, Integer> ects : ue.ects().entrySet()) {
                    Matiere matiere = ects.getKey();
                    Integer coef = ects.getValue();
                    if (etudiant.notes().containsKey(matiere)) {
                        Double note = etudiant.notes().get(matiere);
                        moyenne = moyenne + note * coef;
                        coefTotal = coefTotal + coef;
                    }

                }

            }
            moyenne = moyenne / coefTotal;
            return moyenne;

        }

    }



    public static final void afficheSiv2(String enTete, Predicate<Etudiant> predicate, Annee annee, Consumer <Etudiant> conso){

        System.out.println(enTete);
        System.out.println("\n");
        for (Etudiant e : annee.etudiants()) {
            if(predicate.test(e)) {
                conso.accept(e);
            }
        }
    }

    public static final Double moyenneIndicative (Etudiant etudiant) {

        Double moyenne = 0.0;
        Integer coef = 0;

        for (UE ue : etudiant.annee().ues()) {
            for (Map.Entry<Matiere, Integer> ects : ue.ects().entrySet()) {

                Matiere matiere = ects.getKey();
                Integer credits = ects.getValue();
                if(etudiant.notes().containsKey(matiere)){
                    Double note = etudiant.notes().get(matiere);
                    moyenne = moyenne + note * credits;
                    coef = coef + credits;
                }else{
                    coef=coef+ects.getValue();
                }

            }
        }

        moyenne = moyenne/coef;
        return moyenne;

    }




    public static void main(String[] args){

        Matiere m1 = new Matiere("MAT1");
        Matiere m2 = new Matiere("MAT2");
        UE ue1 = new UE("UE1", Map.of(m1, 2, m2, 2));
        Matiere m3 = new Matiere("MAT3");
        UE ue2 = new UE("UE2", Map.of(m3, 1));
        Annee a1 = new Annee(Set.of(ue1, ue2));
        Etudiant e1 = new Etudiant("39001", "Alice", "Merveille", a1);
        e1.noter(m1, 12.0);
        e1.noter(m2, 14.0);
        e1.noter(m3, 10.0);
        System.out.println(e1);
        Etudiant e2 = new Etudiant("39002", "Bob", "Eponge", a1);
        e2.noter(m1, 20.0);
        e2.noter(m3, 20.0);
        Etudiant e3 = new Etudiant("39003", "Charles", "Chaplin", a1);
        e3.noter(m1, 18.0);
        e3.noter(m2, 5.0);
        e3.noter(m3, 14.0);

        Predicate <Etudiant> tousLesEtudiants = x -> true;


        Predicate <Etudiant> aDef = x -> {
            Set<Matiere> toutesLesMatieresDeLEtudiant = toutesLesMatieresDeLAnnee(x.annee());
            for (Matiere m : toutesLesMatieresDeLEtudiant){
                if(!x.notes().containsKey(m)){

                    return true;

                }
            }
            return false;
        };

        Predicate <Etudiant> aNoteEliminatoire = x-> {

            for (Map.Entry<Matiere,Double> note : x.notes().entrySet()) {
                if (note.getValue() < 6) {
                    return true;

                }
            }
            return false;

        };

        Predicate <Etudiant> naPasLaMoyennev1 = x-> {
        try {
         return  moyenne(x, aDef.test(x)) < 10;

        }
        catch (NullPointerException e)
        {
            System.err.printf("La moyenne de l'étudiant %s %s n'a pas pu être calculée%n", x.prenom(), x.nom());
            return false;
        }
        }; // Lorsque l'on tombe sur un etudiant defaillant on retorne null ce qui provoque une erreur à la compilation

        Predicate <Etudiant> naPasLaMoyennev2 =  naPasLaMoyennev1.or(aDef) ;

        Predicate <Etudiant> session2v1 =   aDef.or(aNoteEliminatoire.or(naPasLaMoyennev1));

        Consumer <Etudiant> sansDef = x-> {
            if(!aDef.test(x)) {
                System.out.println(x.prenom() + " " + x.nom() + " " + moyenne(x, aDef.test(x)));
            } else {
                System.out.println(x.prenom() + " " + x.nom() + " Défaillant");
            }
        };

        Consumer <Etudiant> avecDef = x-> System.out.println(x.prenom() + " " + x.nom() + " " + moyenneIndicative(x));

        Predicate <Etudiant> naPasLaMoyenneGeneralise = x->{ if (moyenneIndicative(x)<10) return true; else return false;};



       // afficheSi("** TOUS LES ETUDIANTS",tousLesEtudiants, a1);
       // afficheSi("** TOUS LES ETUDIANTS DEFAILLANT",aDef, a1)
       // afficheSi("** TOUS LES ETUDIANS ELIMINE", aNoteEliminatoire,a1);
        // afficheSi("** ETUDIANT SOUS LA MOYENNE ",naPasLaMoyennev1,a1);
        //afficheSi("** ETUDIANT SOUS LA MOYENNE V2",naPasLaMoyennev2,a1);
       // afficheSi("** ETUDIANT EN SESSION2",session2v1,a1);
       // afficheSiv2("** MOYENNE DES ETUDIANTS",tousLesEtudiants,a1,sansDef);
       // afficheSiv2("** MOYENNE DES ETUDIANTS",tousLesEtudiants,a1,avecDef);
       // afficheSiv2("TOUS LES ETUDIANTS SOUS LA MOYENNE INDICATIVE",naPasLaMoyenneGeneralise,a1,avecDef);








    }


}

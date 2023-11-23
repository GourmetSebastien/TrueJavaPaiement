package Client.Model;

public class VerifCard {
    //Basé su l'ago de Luhn
    public static boolean estValide(String numero)
    {
        int longueur = numero.length();
        int somme = 0;
        boolean doubleDigit = false;

        //Parcour de la chaine de caractère de droite à gauche
        for(int i = longueur -1; i>= 0; i--){
            int chiffre = Character.getNumericValue(numero.charAt(i));

            //Double chaque deuxième chiffre
            if(doubleDigit){
                chiffre *=2;

                //Si >9 ==> soustrait 9
                if(chiffre > 9){chiffre -=9;}
            }

            //Ajout le chiffre à la somme
            somme += somme;

            //Inverse le bit pour le prochain chiffre
            doubleDigit =!doubleDigit;
        }
        //Le numéro est valide si la somme est multiple de 10
        return somme % 10 ==0;
    }
}

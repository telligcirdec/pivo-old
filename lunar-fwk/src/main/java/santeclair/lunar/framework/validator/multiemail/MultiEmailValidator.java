package santeclair.lunar.framework.validator.multiemail;

import java.util.StringTokenizer;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.internal.constraintvalidators.EmailValidator;


/**
 * Permet la v�rification d'un attribut d'un bean contenant une liste de mail avec un s�parateur.
 * Le champ doit �tre annot� avec l'annotation du m�me package MultiMail.
 * 
 * @author cgillet
 * 
 */
public class MultiEmailValidator implements ConstraintValidator<MultiEmail, String> {

    /**
     * Annotation pour la validation.
     */
    private MultiEmail constraintAnnotation;

    public void initialize(MultiEmail constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        if (StringUtils.isBlank(object)) {
            return false;
        } else {
            String[] emailTokens = tokenizeEmail(object);
            EmailValidator emailValidator = new EmailValidator();
            for (String email : emailTokens) {
                boolean isValid = emailValidator.isValid(email, constraintContext);
                if (!isValid) {
                    return isValid;
                }
            }
            if (constraintAnnotation.checkDouble()) {
                boolean checkDouble = checkDouble(emailTokens);
                if (checkDouble) {
                    constraintContext.disableDefaultConstraintViolation();
                    constraintContext.buildConstraintViolationWithTemplate("{santeclair.lunar.framework.validation.multiemail.doublon.message}")
                                    .addConstraintViolation();
                }
                return !checkDouble;
            }
        }
        return true;
    }

    /**
     * Permet le parcours de l'objet sous contrainte selon le token
     * d�finie dans l'annotation et le remplissage d'un tableau de String
     * token par token.
     * 
     * @param object La chaine de caract�re � d�couper
     * @return Le tableau de string avec les token
     */
    private String[] tokenizeEmail(String object) {
        StringTokenizer multiEmailTokenizer = new StringTokenizer(object, constraintAnnotation.token());
        int countToken = multiEmailTokenizer.countTokens();

        String[] emailTokens = new String[countToken];

        for (int i = 0; i < countToken; i++) {
            emailTokens[i] = StringUtils.trim(multiEmailTokenizer.nextToken());
        }

        return emailTokens;
    }

    /**
     * Renvoie true si une valeur dans le tableau est en double.
     * 
     * @param emailTokens Le tableau des chaines de caracteres
     * @return Renvoie true d�s qu'une valeur dans le tableau est trouv�e en double.
     */
    private boolean checkDouble(String[] emailTokens) {
        for (String token : emailTokens) {
            boolean isUnique = isUnique(token, emailTokens);
            if (!isUnique) {
                return !isUnique;
            }
        }
        return false;
    }

    /**
     * Verifie si le string pass� en param�tre n'est pr�sent
     * qu'une seule fois dans le tableau �galement pass� en param�tre.
     * 
     * @param s Le string a compar�.
     * @param array Le tableau de string servant � la comparaison.
     * @return True si le string pass� en param�tre est pr�sent une et une seule fois.
     *         False dans le cas contraire, c'est � dire soit plusieurs
     *         fois soit aucune fois.
     */
    private boolean isUnique(String s, String[] array) {
        int count = 0;
        for (int j = 0; j < array.length; j++) {
            if (array[j] != null && s.equals(array[j])) {
                count++;
            }
        }
        return count == 1;
    }

}

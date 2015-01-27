package santeclair.lunar.framework.enumeration;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe abstraite fournie des méthodes statiques permettant de travailler sur des énumérations qui implémentent
 * {@link santeclair.lunar.framework.enumeration.CodeEnum}. Ces énumérations ont la particularité d'avoir un attribut code qui doit
 * permettre d'identifier de manière unique un des éléments de l'énumération.
 * 
 * @author cgillet
 * 
 */
public abstract class AbstractEnumTools {

    /**
     * Cette méthode permet de renvoyer l'énumération correspondant au code passé en paramètre.
     * 
     * @param <ENUM> Le type de l'énumération. Celle-ci doit implémenter {@link santeclair.lunar.framework.enumeration.CodeEnum}.
     * @param enumerationClazz La classe de l'énumération.
     * @param code Le code recherché.
     * @return L'énumération correspondante au code passé en paramètre. Null si aucun code ne correspond.
     */
    public static <ENUM extends CodeEnum<?>> ENUM findEnumValuesByCode(Class<ENUM> enumerationClazz,
                    String code) {
        if (enumerationClazz == null) {
            throw new IllegalArgumentException("enumerationClazz parameter is null. Enum class must be defined.");
        }
        ENUM[] enums = enumerationClazz.getEnumConstants();
        List<ENUM> enumFounds = new ArrayList<ENUM>();
        if (enums != null && enums.length > 0) {
            for (ENUM enumeration : enums) {
                String tempCodeFromEnum = enumeration.getCode();
                String tempCode = code;
                if (tempCodeFromEnum.equalsIgnoreCase(tempCode)) {
                    enumFounds.add(enumeration);
                }
            }
        }
        if (enumFounds.size() == 1) {
            return enumFounds.get(0);
        } else if (enumFounds.size() > 1) {
            throw new IllegalArgumentException("More than one enum found for code : " + code + " => " + enumFounds);
        }
        return null;
    }

    /**
     * Cette méthode permet de renvoyer l'énumération correspondant au code passé en paramètre.
     * 
     * @param <ENUM> Le type de l'énumération. Celle-ci doit implémenter {@link santeclair.lunar.framework.enumeration.CodeEnum}.
     * @param enumerationClazz La classe de l'énumération.
     * @param code Le code recherché.
     * @return L'énumération correspondante au code passé en paramètre. Null si aucun code ne correspond.
     */
    public static <ENUM extends LibelleEnum<?>> ENUM findEnumValuesByLibelle(Class<ENUM> enumerationClazz,
                    String libelle) {
        if (enumerationClazz == null) {
            throw new IllegalArgumentException("enumerationClazz parameter is null. Enum class must be defined.");
        }
        if (libelle != null) {
            ENUM[] enums = enumerationClazz.getEnumConstants();
            List<ENUM> enumFounds = new ArrayList<ENUM>();
            if (enums != null && enums.length > 0) {
                for (ENUM enumeration : enums) {
                    String tempLibelleFromEnum = enumeration.getLibelle();
                    tempLibelleFromEnum = Normalizer.normalize(tempLibelleFromEnum, Normalizer.Form.NFD);
                    tempLibelleFromEnum = tempLibelleFromEnum.replaceAll("\\W", "");

                    String tempLibelle = libelle;
                    tempLibelle = Normalizer.normalize(tempLibelle, Normalizer.Form.NFD);
                    tempLibelle = tempLibelle.replaceAll("\\W", "");

                    if (tempLibelleFromEnum.equalsIgnoreCase(tempLibelle)) {
                        enumFounds.add(enumeration);
                    }
                }
            }
            if (enumFounds.size() == 1) {
                return enumFounds.get(0);
            } else if (enumFounds.size() > 1) {
                throw new IllegalArgumentException("More than one enum found for libelle : " + libelle + " => " + enumFounds);
            }
        }
        return null;
    }

    /**
     * Permet de récupérer une liste contenant les codes de l'énumération.
     * 
     * @param <ENUM> Le type de l'énumération.
     * @param <CODE> Le type du code.
     * @param enumerationClazz La classe de l'énumération.
     * @return Une list contenant une liste exhaustive des codes de l'énumération.
     */
    public static <ENUM extends CodeEnum<?>> List<String> getCodes(Class<ENUM> enumerationClazz) {
        if (enumerationClazz == null) {
            throw new IllegalArgumentException("enumerationClazz parameter is null. Enum class must be defined.");
        }
        ENUM[] enums = enumerationClazz.getEnumConstants();

        List<String> listToFill = new ArrayList<String>(enums.length);

        if (enums.length > 0) {
            for (ENUM enumeration : enums) {
                listToFill.add(enumeration.getCode());
            }
        }
        return listToFill;
    }
}

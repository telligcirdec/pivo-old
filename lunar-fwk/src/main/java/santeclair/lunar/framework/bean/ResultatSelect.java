/**
 * 
 */
package santeclair.lunar.framework.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import santeclair.lunar.framework.util.SqlUtils;

import com.google.common.base.Preconditions;

/**
 * Structure portant plusieurs informations sur le r�sultat d'un SELECT en JDBC.
 * 
 * @author jfourmond
 * 
 */
public class ResultatSelect {

    private String sqlQuery;
    private Integer nbColonnes;
    private String[] nomsColonnes;
    private String[] typesColonnes;

    /**
     * Les r�sultats du SELECT sous forme de List.
     * La liste est tri�e selon le contenu du SELECT.
     */
    private List<String[]> listLignesResultat;

    /**
     * Les r�sultats du SELECT sous forme de Map.
     * La cl� de la Map est l'identifiant de la ligne de r�sultats, et cet identifiant est aussi pr�sent dans la ligne (1�re colonne).
     */
    private Map<String, String[]> mapLignesResultat;

    /*
     * =======================================================*
     * constructeurs
     * =======================================================
     */

    /**
     * Constructeur avec tous les param�tres sauf les r�sultats, qu'on ajoutera plus tard, un par un.
     */
    public ResultatSelect(String sqlQuery, Integer nbColonnes, String[] nomsColonnes, String[] typesColonnes) {
        Preconditions.checkArgument(nomsColonnes != null && nomsColonnes.length > 0,
                        "le tableau de noms de colonnes doit �tre not null et non vide");
        Preconditions.checkArgument(typesColonnes != null && typesColonnes.length > 0,
                        "le tableau de types de colonnes doit �tre not null et non vide");
        this.sqlQuery = sqlQuery;
        this.nbColonnes = nbColonnes;
        Integer nbNomsColonnes = nomsColonnes.length;
        if (!nbColonnes.equals(nbNomsColonnes)) {
            throw new IllegalArgumentException("On a " + nbNomsColonnes + " en param�tre alors qu'on en attend " + nbColonnes);
        }
        this.nomsColonnes = nomsColonnes.clone();
        this.typesColonnes = typesColonnes.clone();
        this.listLignesResultat = new ArrayList<String[]>();
        this.mapLignesResultat = new HashMap<String, String[]>();
    }

    /*
     * =======================================================*
     * code m�tier
     * =======================================================
     */

    /**
     * Ajoute une ligne � la liste des r�sultats.
     */
    public void addLigne(String[] ligne) {
        Integer nbColonnesLigne = ligne.length;
        if (!nbColonnes.equals(nbColonnesLigne)) {
            throw new IllegalArgumentException("La ligne de r�sultats en param�tre a " + nbColonnesLigne + " colonnes alors qu'on en attend "
                            + nbColonnes);
        }
        listLignesResultat.add(ligne);
        String identifiant = ligne[0];
        mapLignesResultat.put(identifiant, ligne);
    }

    /**
     * Renvoie la ligne de r�sultats correspondant � l'identifiant en param�tre.
     */
    public String[] getLigne(String identifiant) {
        String[] ligne = mapLignesResultat.get(identifiant);
        return ligne;
    }

    /**
     * Renvoie un Set contenant les identifiants de toutes les lignes de r�sultat.
     * 
     * @return un {@link HashSet}
     */
    public Set<String> getIdentifiants() {
        return mapLignesResultat.keySet();
    }

    /**
     * Renvoie le num�ro de la colonne correspondant au nom en param�tre.
     * 
     * @param nomColonne le nom de colonne, peu importe la casse.
     * @return un entier � partir de 0.
     * @throws IllegalArgumentException si aucune colonne n'a ce nom.
     */
    public int getNumeroColonne(String nomColonne) throws IllegalArgumentException {
        for (int i = 0; i < nbColonnes; i++) {
            if (StringUtils.equalsIgnoreCase(nomColonne, nomsColonnes[i])) {
                return i;
            }
        }
        throw new IllegalArgumentException("Nom de colonne inconnu : " + nomColonne);
    }

    /**
     * Renvoie la ligne de r�sultats en param�tre apr�s avoir d�doubl� les �ventuels apostrophes � l'int�rieur des r�sultats,
     * remplac� les valeurs null par la chaine "NULL",
     * et entour� les types textuels (y compris les dates) par des apostrophes.
     * (Sans cel�, les r�sultats ne sont pas r�utilisables dans des instructions SQL).
     */
    public String[] escapeApostrophesEtNulls(String[] ligneResultats) {
        String[] ligneResultatsSansApostrophes = ligneResultats;
        for (int i = 0; i < nbColonnes; i++) {
            String typeColonne = typesColonnes[i];
            if (ligneResultatsSansApostrophes[i] == null) {
                ligneResultatsSansApostrophes[i] = SqlUtils.NULL;
            } else {
                if (SqlUtils.isTypeTextuel(typeColonne)) {
                    ligneResultatsSansApostrophes[i] = SqlUtils.quote(ligneResultats[i]);
                }
            }
        }
        return ligneResultatsSansApostrophes;
    }

    /**
     * Convertit les r�sultats du SELECT SQL en page HTML.
     */
    public String toHtml() {
        int nbLignesResultats = listLignesResultat.size();
        StringBuilder sb = new StringBuilder(1000);
        sb.append("<html>\n<head/>\n<body>\n");
        sb.append("<h2>" + sqlQuery + "</h2>\n");
        sb.append("<h3>" + nbLignesResultats + " r�sultat(s)</h3>\n");
        return contruireResultat(sb);
    }

    /**
     * Convertit les r�sultats du SELECT SQL en page Excel.
     */
    public String toExcel() {
        StringBuilder sb = new StringBuilder(1000);
        sb.append("<html>\n<head/>\n<body>\n");
        return contruireResultat(sb);
    }

    /**
     * construit une squelette commune HTML et EXCEL
     * 
     */
    public String contruireResultat(StringBuilder sb) {
        sb.append("<table border=\"1\"><tr>\n");
        for (String nomColonne : nomsColonnes) {
            sb.append("<td>");
            sb.append(nomColonne.replaceAll("_", " "));
            sb.append("</td>\n");
        }
        sb.append("</tr>\n");

        for (String[] ligneResultats : listLignesResultat) {
            sb.append("<tr>\n");
            for (String colonne : ligneResultats) {
                sb.append("<td>");
                sb.append(colonne);
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }

        sb.append("</table></body></html>\n");
        String html = sb.toString();
        return html;
    }

    /*
     * =======================================================*
     * getters & setters
     * =======================================================
     */

    /**
     * @return the query
     */
    public String getSqlQuery() {
        return sqlQuery;
    }

    /**
     * @return the nbColonnes
     */
    public Integer getNbColonnes() {
        return nbColonnes;
    }

    /**
     * @return the nomColonnes
     */
    public String[] getNomsColonnes() {
        return nomsColonnes;
    }

    /**
     * @return the lignes
     */
    public List<String[]> getListLignesResultat() {
        return listLignesResultat;
    }

    /**
     * @return the typesColonnes
     */
    public String[] getTypesColonnes() {
        return typesColonnes;
    }

    /**
     * @return the mapLignesResultats
     */
    public Map<String, String[]> getMapLignesResultat() {
        return mapLignesResultat;
    }

}

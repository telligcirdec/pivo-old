package santeclair.lunar.framework.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import santeclair.lunar.framework.bean.ResultatSelect;

/**
 * Service permettant d'exécuter des requêtes SQL en base via JDBC.
 * 
 * @author jfourmond
 * 
 */
public class JdbcUtils {

    /**
     * Exécute un select en base et renvoie les résultats sous forme d'un objet {@link ResultatSelect}.
     * 
     * @param dataSource la dataSource utilisée pour se connecter à la base.
     * @param sqlQuery la requête SQL, qui doit contenir un et un seul SELECT.
     * @throws SQLException si le SQL contient autre chose qu'un et un seul SELECT
     */
    public static ResultatSelect select(Connection connexion, String sqlQuery) throws SQLException {

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            /* Exécution de la requête */
            statement = connexion.createStatement(); // NOSONAR
            resultSet = statement.executeQuery(sqlQuery); // NOSONAR
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            /* Création de l'objet contenant les résultats */
            int nbColonnes = resultSetMetaData.getColumnCount();
            String[] nomsColonnes = new String[nbColonnes];
            String[] typesColonnes = new String[nbColonnes];

            for (int i = 1; i <= nbColonnes; i++) {
                nomsColonnes[i - 1] = resultSetMetaData.getColumnLabel(i);
                String columnType = resultSetMetaData.getColumnTypeName(i);
                typesColonnes[i - 1] = columnType.toLowerCase();
            }
            ResultatSelect result = new ResultatSelect(sqlQuery, nbColonnes, nomsColonnes, typesColonnes);
            while (resultSet.next()) {
                String[] row = new String[nbColonnes];
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    row[i - 1] = resultSet.getString(i);
                }
                result.addLigne(row);
            }
            return result;

        } finally {
            /* Libération des ressources */
            org.springframework.jdbc.support.JdbcUtils.closeStatement(statement);
            org.springframework.jdbc.support.JdbcUtils.closeResultSet(resultSet);
        }

    }

}

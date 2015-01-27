package santeclair.lunar.framework.web.selenium;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Predicate;

/**
 * Classe abstraite d'assistance � la cr�ation de tests fonctionnels via Selenium.
 * 
 * @author cgillet
 * 
 */
public abstract class SeleniumHelper {

    /**
     * Permet la cr�ation d'un screenshot pendant le d�roul� du test.
     * 
     * @param driver Le driver du tests selenium (par manque de temps seul InternetExplorerDriver est g�r�. Un wrapper
     *            devrait �tre cr�� pour ouvrir cette m�thode aux autres drivers : firefox, chrome...)
     * @param path Le chemin ou le screenshot devra �tre sauvegard�. Doit se finir par un s�parateur/
     * @param screenshotName Le nom du screenshot sans extension.
     */
    public static void getScreenShotAs(final InternetExplorerDriver driver, final String path, final String screenshotName) {

        driver.getScreenshotAs(new OutputType<File>() {

            OutputType<byte[]> wrappedOutputFile = OutputType.BYTES;

            public File convertFromBase64Png(String base64Png) {
                return save(wrappedOutputFile.convertFromBase64Png(base64Png));
            }

            public File convertFromPngBytes(byte[] png) {
                return save(png);
            }

            private File save(byte[] data) {
                OutputStream stream = null;
                try {
                    File tmpFile = new File(path + screenshotName + ".png");
                    stream = new FileOutputStream(tmpFile);
                    stream.write(data);
                    return tmpFile;
                } catch (IOException e) {
                    throw new WebDriverException(e);
                } finally {
                    if (stream != null) {
                        try {
                            stream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    /**
     * Permet de g�rer une attente d'affichage d'un �l�ment lors du d�roul� du test.
     * 
     * @param driver Le driver du test en cours.
     * @param by La s�lection de l'�l�ment � attendre.
     * @param pooling Le temps entre chaque v�rification de la pr�sence de l'�l�ment (ms).
     * @param timeout Le temps maximum d'attente pour v�rifier la pr�sence d'un �l�ment (ms).
     */
    public static void fluentWaitDisplayedBy(final WebDriver driver, final By by, final long pooling, final long timeout) {
        fluentWaitPredicated(driver, by, pooling, timeout, new Predicate<By>() {
            public boolean apply(By by) {
                try {
                    return driver.findElement(by).isDisplayed();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return false;
                }
            }
        });
    }

    /**
     * Peremt de g�rer une attente selon les conditions v�rifi�es dans l'objet predicate.
     * 
     * @param driver Le driver du test en cours.
     * @param by La s�lection de l'�l�ment � attendre.
     * @param pooling Le temps entre chaque v�rification de la pr�sence de l'�l�ment (ms).
     * @param timeout Le temps maximum d'attente pour v�rifier la pr�sence d'un �l�ment (ms).
     * @param predicate La condition permettant de sortir ou non de l'attente.
     */
    public static void fluentWaitPredicated(final WebDriver driver, final By by, final long pooling, final long timeout, Predicate<By> predicate) {
        FluentWait<By> fluentWaitCodeSuppVerre = new FluentWait<By>(by);
        fluentWaitCodeSuppVerre.pollingEvery(pooling, TimeUnit.MILLISECONDS);
        fluentWaitCodeSuppVerre.withTimeout(timeout, TimeUnit.MILLISECONDS);
        fluentWaitCodeSuppVerre.until(predicate);
    }

    /**
     * Permet de marquer un temps d'arret dans le processus. Un simple Thread.sleep pour le moment faute de temps pour
     * trouver une solution plus safe.
     * 
     * @param timeToWait Temps d'attente en ms.
     * @throws InterruptedException
     */
    public static void waitForLoad(final long timeToWait) throws InterruptedException {
        Thread.sleep(timeToWait);
    }

    /**
     * Permet de v�rifier la pr�sence ou non d'un �l�ment.
     * 
     * @param driver Le driver du test en cours.
     * @param by La logique de s�lection de l'�l�ment � v�rifier.
     * @return Vraie si l'�l�ment est trouv�, false s'il n'est pas trouv�.
     */
    public static boolean isElementPresent(final WebDriver driver, final By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Permet d'accepter ou non une alerte JS.
     * 
     * @param driver Le driver du test en cours.
     * @param acceptAlert True pour accepter le message, false dans le cas contraire.
     * @return Le message contenu dans la popup.
     */
    public static String closeAlertAndGetItsText(final WebDriver driver, final boolean acceptAlert) {
        Alert alert = driver.switchTo().alert();
        if (acceptAlert) {
            alert.accept();
        } else {
            alert.dismiss();
        }
        return alert.getText();
    }

}

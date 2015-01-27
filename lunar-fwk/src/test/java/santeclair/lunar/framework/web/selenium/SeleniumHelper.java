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
 * Classe abstraite d'assistance à la création de tests fonctionnels via Selenium.
 * 
 * @author cgillet
 * 
 */
public abstract class SeleniumHelper {

    /**
     * Permet la création d'un screenshot pendant le déroulé du test.
     * 
     * @param driver Le driver du tests selenium (par manque de temps seul InternetExplorerDriver est géré. Un wrapper
     *            devrait être créé pour ouvrir cette méthode aux autres drivers : firefox, chrome...)
     * @param path Le chemin ou le screenshot devra être sauvegardé. Doit se finir par un séparateur/
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
     * Permet de gérer une attente d'affichage d'un élément lors du déroulé du test.
     * 
     * @param driver Le driver du test en cours.
     * @param by La sélection de l'élément à attendre.
     * @param pooling Le temps entre chaque vérification de la présence de l'élément (ms).
     * @param timeout Le temps maximum d'attente pour vérifier la présence d'un élément (ms).
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
     * Peremt de gérer une attente selon les conditions vérifiées dans l'objet predicate.
     * 
     * @param driver Le driver du test en cours.
     * @param by La sélection de l'élément à attendre.
     * @param pooling Le temps entre chaque vérification de la présence de l'élément (ms).
     * @param timeout Le temps maximum d'attente pour vérifier la présence d'un élément (ms).
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
     * Permet de vérifier la présence ou non d'un élément.
     * 
     * @param driver Le driver du test en cours.
     * @param by La logique de sélection de l'élément à vérifier.
     * @return Vraie si l'élément est trouvé, false s'il n'est pas trouvé.
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

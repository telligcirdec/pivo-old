package santeclair.lunar.framework.aspect.processor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath:/lunarTestProcessorApplicationContext.xml"})
@RunWith(value = SpringJUnit4ClassRunner.class)
public class AspectProcessIntegrationTest {

    @Autowired
    private ProcessingMethod processingMethod;

    /**
     * Vérifie que l'objet renvoyé est bien passé dans la logique de
     * preprocessing.
     */
    @Test
    public void testPostProcessingSpringContext() {
        ObjectToProcessBySpring objectToProcess = processingMethod.postProcessingSpringContext();
        assertTrue(objectToProcess.getHasBeenProcess());
    }

    /**
     * Vérifie que l'objet passé en paramètre est bien traité en amont.
     */
    @Test
    public void testPreProcessingSpringContext() {
        ObjectToProcessBySpring objectToProcess = new ObjectToProcessBySpring();
        processingMethod.preProcessingSpringContext(objectToProcess);
        assertTrue(objectToProcess.getHasBeenProcess());
    }

    /**
     * Vérifie que l'objet passé en parametre est bien traité en amont
     * et que l'objet renvoyé également. Garantie une parfaite étanchéité
     * entre pre et post processor.
     */
    @Test
    public void testPostAndPreProcessingSpringContext() {
        ObjectToProcessBySpring objectToProcess = new ObjectToProcessBySpring();
        ObjectToProcessBySpring objectToProcessReturn = processingMethod.postAndPreProcessingSpringContext(objectToProcess);
        assertTrue(objectToProcess.getHasBeenProcess());
        assertTrue(objectToProcessReturn.getHasBeenProcess());
    }

    /**
     * Vérifie que plusieurs objets peuvent être pre traité.
     */
    @Test
    public void testSeveralPreProcessingSpringContext() {
        ObjectToProcessBySpring objectToProcess = new ObjectToProcessBySpring();
        ObjectToProcessBySpring objectToProcess2 = new ObjectToProcessBySpring();
        processingMethod.severalPreProcessingSpringContext(objectToProcess, objectToProcess2);
        assertTrue(objectToProcess.getHasBeenProcess());
        assertTrue(objectToProcess2.getHasBeenProcess());
    }

    /**
     * Vérifie que seuls les objets annotés à l'aide de @PreProcess sont traités.
     */
    @Test
    public void testSeveralPreProcessingAndNoneForOneSpringContext() {
        ObjectToProcessBySpring objectToProcess = new ObjectToProcessBySpring();
        ObjectToProcessBySpring objectToProcess2 = new ObjectToProcessBySpring();
        ObjectToProcessBySpring objectToProcess3 = new ObjectToProcessBySpring();
        processingMethod.severalPreProcessingAndNoneForOneSpringContext(objectToProcess, objectToProcess2, objectToProcess3);
        assertTrue(objectToProcess.getHasBeenProcess());
        assertTrue(objectToProcess2.getHasBeenProcess());
        assertFalse(objectToProcess3.getHasBeenProcess());

    }

    /**
     * Vérifie que le post processing avec la classe de post processing passée par annotation fonctionne.
     */
    @Test
    public void testPostProcessingByClazz() {
        ObjectToProcess objectToProcessReturn = processingMethod.postProcessingByClazz();
        assertTrue(objectToProcessReturn.getHasBeenProcess());
    }

    /**
     * Vérifie que le pre processing avec la classe de pre processing passée par annotation fonctionne.
     */
    @Test
    public void testPreProcessingByClazz() {
        ObjectToProcess objectToProcess = new ObjectToProcess();
        processingMethod.preProcessingByClazz(objectToProcess);
        assertTrue(objectToProcess.getHasBeenProcess());
    }

    /**
     * Vérifie que le post processing avec la classe de post processing passée par package fonctionne.
     */
    @Test
    public void testPostProcessingByPackage() {
        ObjectToProcess objectToProcessReturn = processingMethod.postProcessingByPackage();
        assertTrue(objectToProcessReturn.getHasBeenProcess());
    }

    /**
     * Vérifie que le pre processing avec la classe de pre processing passée par package fonctionne.
     */
    @Test
    public void testPreProcessingByPackage() {
        ObjectToProcess objectToProcess = new ObjectToProcess();
        processingMethod.preProcessingByPackage(objectToProcess);
        assertTrue(objectToProcess.getHasBeenProcess());
    }

    /**
     * Vérifie que le pre processing avec la classe de pre processing passée par package fonctionne
     * dans un contexte Spring.
     */
    @Test
    public void testPreProcessingByPackageBySpring() {
        ObjectToProcessBySpring objectToProcessBySpring = new ObjectToProcessBySpring();
        processingMethod.preProcessingByPackageAndContextSpring(objectToProcessBySpring);
        assertTrue(objectToProcessBySpring.getHasBeenProcess());
    }

    /**
     * Vérifie que le pre processing avec la classe de pre processing passée par package fonctionne
     * dans un contexte Spring.
     */
    @Test
    public void testNoPreProcessingByPackageBySpring() {
        ObjectToProcess objectToProcessBySpring = new ObjectToProcess();
        processingMethod.noPreProcessingByPackageAndContextSpring(objectToProcessBySpring);
        assertFalse(objectToProcessBySpring.getHasBeenProcess());
    }

    /**
     * Vérifie que le post processing avec la classe de pre processing passée par package ne
     * fonctionne pas dans un contexte Spring.
     */
    @Test
    public void testPostProcessingByPackageBySpring() {
        ObjectToProcessBySpring objectToProcess = processingMethod.postProcessingByPackageBySpring();
        assertTrue(objectToProcess.getHasBeenProcess());
    }

    /**
     * Vérifie que le post processing avec la classe de pre processing passée par package ne
     * fonctionne pas dans un contexte Spring.
     */
    @Test
    public void testNoPostProcessingByPackageBySpring() {
        ObjectToProcess objectToProcess = processingMethod.noPostProcessingByPackageBySpring();
        assertFalse(objectToProcess.getHasBeenProcess());
    }

    /**
     * Vérifie que le pre processing avec la classe de pre processing passée par clazz fonctionne
     * dans un contexte Spring.
     */
    @Test
    public void testPreProcessingByClazzBySpring() {
        ObjectToProcessBySpring objectToProcessBySpring = new ObjectToProcessBySpring();
        processingMethod.preProcessingByClazzAndContextSpring(objectToProcessBySpring);
        assertTrue(objectToProcessBySpring.getHasBeenProcess());
    }

    /**
     * Vérifie que le pre processing avec la classe de pre processing passée par clazz fonctionne
     * dans un contexte Spring.
     */
    @Test
    public void testNoPreProcessingByClazzBySpring() {
        ObjectToProcess objectToProcessBySpring = new ObjectToProcess();
        processingMethod.noPreProcessingByClazzAndContextSpring(objectToProcessBySpring);
        assertFalse(objectToProcessBySpring.getHasBeenProcess());
    }

    /**
     * Vérifie que le post processing avec la classe de pre processing passée par Clazz ne
     * fonctionne pas dans un contexte Spring.
     */
    @Test
    public void testPostProcessingByClazzBySpring() {
        ObjectToProcessBySpring objectToProcess = processingMethod.postProcessingByClazzBySpring();
        assertTrue(objectToProcess.getHasBeenProcess());
    }

    /**
     * Vérifie que le post processing avec la classe de pre processing passée par Clazz ne
     * fonctionne pas dans un contexte Spring.
     */
    @Test
    public void testNoPostProcessingByClazzBySpring() {
        ObjectToProcess objectToProcess = processingMethod.noPostProcessingByClazzBySpring();
        assertFalse(objectToProcess.getHasBeenProcess());
    }

}

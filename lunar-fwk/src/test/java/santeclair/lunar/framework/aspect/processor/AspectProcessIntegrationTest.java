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
     * V�rifie que l'objet renvoy� est bien pass� dans la logique de
     * preprocessing.
     */
    @Test
    public void testPostProcessingSpringContext() {
        ObjectToProcessBySpring objectToProcess = processingMethod.postProcessingSpringContext();
        assertTrue(objectToProcess.getHasBeenProcess());
    }

    /**
     * V�rifie que l'objet pass� en param�tre est bien trait� en amont.
     */
    @Test
    public void testPreProcessingSpringContext() {
        ObjectToProcessBySpring objectToProcess = new ObjectToProcessBySpring();
        processingMethod.preProcessingSpringContext(objectToProcess);
        assertTrue(objectToProcess.getHasBeenProcess());
    }

    /**
     * V�rifie que l'objet pass� en parametre est bien trait� en amont
     * et que l'objet renvoy� �galement. Garantie une parfaite �tanch�it�
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
     * V�rifie que plusieurs objets peuvent �tre pre trait�.
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
     * V�rifie que seuls les objets annot�s � l'aide de @PreProcess sont trait�s.
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
     * V�rifie que le post processing avec la classe de post processing pass�e par annotation fonctionne.
     */
    @Test
    public void testPostProcessingByClazz() {
        ObjectToProcess objectToProcessReturn = processingMethod.postProcessingByClazz();
        assertTrue(objectToProcessReturn.getHasBeenProcess());
    }

    /**
     * V�rifie que le pre processing avec la classe de pre processing pass�e par annotation fonctionne.
     */
    @Test
    public void testPreProcessingByClazz() {
        ObjectToProcess objectToProcess = new ObjectToProcess();
        processingMethod.preProcessingByClazz(objectToProcess);
        assertTrue(objectToProcess.getHasBeenProcess());
    }

    /**
     * V�rifie que le post processing avec la classe de post processing pass�e par package fonctionne.
     */
    @Test
    public void testPostProcessingByPackage() {
        ObjectToProcess objectToProcessReturn = processingMethod.postProcessingByPackage();
        assertTrue(objectToProcessReturn.getHasBeenProcess());
    }

    /**
     * V�rifie que le pre processing avec la classe de pre processing pass�e par package fonctionne.
     */
    @Test
    public void testPreProcessingByPackage() {
        ObjectToProcess objectToProcess = new ObjectToProcess();
        processingMethod.preProcessingByPackage(objectToProcess);
        assertTrue(objectToProcess.getHasBeenProcess());
    }

    /**
     * V�rifie que le pre processing avec la classe de pre processing pass�e par package fonctionne
     * dans un contexte Spring.
     */
    @Test
    public void testPreProcessingByPackageBySpring() {
        ObjectToProcessBySpring objectToProcessBySpring = new ObjectToProcessBySpring();
        processingMethod.preProcessingByPackageAndContextSpring(objectToProcessBySpring);
        assertTrue(objectToProcessBySpring.getHasBeenProcess());
    }

    /**
     * V�rifie que le pre processing avec la classe de pre processing pass�e par package fonctionne
     * dans un contexte Spring.
     */
    @Test
    public void testNoPreProcessingByPackageBySpring() {
        ObjectToProcess objectToProcessBySpring = new ObjectToProcess();
        processingMethod.noPreProcessingByPackageAndContextSpring(objectToProcessBySpring);
        assertFalse(objectToProcessBySpring.getHasBeenProcess());
    }

    /**
     * V�rifie que le post processing avec la classe de pre processing pass�e par package ne
     * fonctionne pas dans un contexte Spring.
     */
    @Test
    public void testPostProcessingByPackageBySpring() {
        ObjectToProcessBySpring objectToProcess = processingMethod.postProcessingByPackageBySpring();
        assertTrue(objectToProcess.getHasBeenProcess());
    }

    /**
     * V�rifie que le post processing avec la classe de pre processing pass�e par package ne
     * fonctionne pas dans un contexte Spring.
     */
    @Test
    public void testNoPostProcessingByPackageBySpring() {
        ObjectToProcess objectToProcess = processingMethod.noPostProcessingByPackageBySpring();
        assertFalse(objectToProcess.getHasBeenProcess());
    }

    /**
     * V�rifie que le pre processing avec la classe de pre processing pass�e par clazz fonctionne
     * dans un contexte Spring.
     */
    @Test
    public void testPreProcessingByClazzBySpring() {
        ObjectToProcessBySpring objectToProcessBySpring = new ObjectToProcessBySpring();
        processingMethod.preProcessingByClazzAndContextSpring(objectToProcessBySpring);
        assertTrue(objectToProcessBySpring.getHasBeenProcess());
    }

    /**
     * V�rifie que le pre processing avec la classe de pre processing pass�e par clazz fonctionne
     * dans un contexte Spring.
     */
    @Test
    public void testNoPreProcessingByClazzBySpring() {
        ObjectToProcess objectToProcessBySpring = new ObjectToProcess();
        processingMethod.noPreProcessingByClazzAndContextSpring(objectToProcessBySpring);
        assertFalse(objectToProcessBySpring.getHasBeenProcess());
    }

    /**
     * V�rifie que le post processing avec la classe de pre processing pass�e par Clazz ne
     * fonctionne pas dans un contexte Spring.
     */
    @Test
    public void testPostProcessingByClazzBySpring() {
        ObjectToProcessBySpring objectToProcess = processingMethod.postProcessingByClazzBySpring();
        assertTrue(objectToProcess.getHasBeenProcess());
    }

    /**
     * V�rifie que le post processing avec la classe de pre processing pass�e par Clazz ne
     * fonctionne pas dans un contexte Spring.
     */
    @Test
    public void testNoPostProcessingByClazzBySpring() {
        ObjectToProcess objectToProcess = processingMethod.noPostProcessingByClazzBySpring();
        assertFalse(objectToProcess.getHasBeenProcess());
    }

}

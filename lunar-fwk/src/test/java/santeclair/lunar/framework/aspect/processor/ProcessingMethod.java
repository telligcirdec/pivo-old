package santeclair.lunar.framework.aspect.processor;

import org.springframework.stereotype.Component;

import santeclair.lunar.framework.aspect.processor.post.PostProcessing;
import santeclair.lunar.framework.aspect.processor.pre.PreProcess;
import santeclair.lunar.framework.aspect.processor.pre.PreProcessing;

@Component
public class ProcessingMethod {

    @PostProcessing
    public ObjectToProcessBySpring postProcessingSpringContext() {
        return new ObjectToProcessBySpring();
    }

    @PreProcessing
    public void preProcessingSpringContext(@PreProcess ObjectToProcessBySpring objectToPreProcess) {
        if (!objectToPreProcess.getHasBeenProcess()) {
            throw new IllegalStateException("Le preProcess ne s'est pas executé correctement.");
        }
    }

    @PreProcessing
    @PostProcessing
    public ObjectToProcessBySpring postAndPreProcessingSpringContext(@PreProcess ObjectToProcessBySpring objectToPreProcess) {
        if (!objectToPreProcess.getHasBeenProcess()) {
            throw new IllegalStateException("Le preProcess ne s'est pas executé correctement.");
        }
        return new ObjectToProcessBySpring();
    }

    @PreProcessing
    public void severalPreProcessingSpringContext(@PreProcess ObjectToProcessBySpring objectToPreProcess,
                    @PreProcess ObjectToProcessBySpring objectToPreProcess2) {
        if (!objectToPreProcess.getHasBeenProcess()) {
            throw new IllegalStateException("Le preProcess ne s'est pas executé correctement.");
        }
        if (!objectToPreProcess2.getHasBeenProcess()) {
            throw new IllegalStateException("Le preProcess ne s'est pas executé correctement.");
        }

    }

    @PreProcessing
    public void severalPreProcessingAndNoneForOneSpringContext(@PreProcess ObjectToProcessBySpring objectToPreProcess,
                    @PreProcess ObjectToProcessBySpring objectToPreProcess2, ObjectToProcessBySpring objectToPreProcess3) {
        if (!objectToPreProcess.getHasBeenProcess()) {
            throw new IllegalStateException("Le preProcess ne s'est pas executé correctement.");
        }
        if (!objectToPreProcess2.getHasBeenProcess()) {
            throw new IllegalStateException("Le preProcess ne s'est pas executé correctement.");
        }
    }

    @PostProcessing(clazz = {PostProcessorImpl.class}, springContext = false)
    public ObjectToProcess postProcessingByClazz() {
        return new ObjectToProcess();
    }

    @PreProcessing
    public void preProcessingByClazz(@PreProcess(clazz = {PreProcessorImpl.class}, springContext = false) ObjectToProcess objectToPreProcess) {
        if (!objectToPreProcess.getHasBeenProcess()) {
            throw new IllegalStateException("Le preProcess ne s'est pas executé correctement.");
        }

    }

    @PostProcessing(packageToScan = "santeclair.lunar.framework.aspect.processor", springContext = false)
    public ObjectToProcess postProcessingByPackage() {
        return new ObjectToProcess();
    }

    @PreProcessing
    public void preProcessingByPackage(@PreProcess(packageToScan = "santeclair.lunar.framework.aspect.processor", springContext = false) ObjectToProcess objectToPreProcess) {
        if (!objectToPreProcess.getHasBeenProcess()) {
            throw new IllegalStateException("Le preProcess ne s'est pas executé correctement.");
        }
    }

    @PreProcessing
    public void preProcessingByPackageAndContextSpring(@PreProcess(packageToScan = "santeclair.lunar.framework.aspect.processor") ObjectToProcessBySpring objectToPreProcessBySpring) {
        if (!objectToPreProcessBySpring.getHasBeenProcess()) {
            throw new IllegalStateException("Le preProcess ne s'est pas executé correctement.");
        }
    }

    @PreProcessing
    public void noPreProcessingByPackageAndContextSpring(@PreProcess(packageToScan = "santeclair.lunar.framework.aspect.processor") ObjectToProcess objectToPreProcess) {
        if (objectToPreProcess.getHasBeenProcess()) {
            throw new IllegalStateException("Le preProcess s'est executé correctement.");
        }
    }

    @PostProcessing(clazz = PostProcessorBySpringImpl.class)
    public ObjectToProcessBySpring postProcessingByClazzBySpring() {
        return new ObjectToProcessBySpring();
    }

    @PostProcessing(clazz = PostProcessorImpl.class)
    public ObjectToProcess noPostProcessingByClazzBySpring() {
        return new ObjectToProcess();
    }

    @PreProcessing
    public void preProcessingByClazzAndContextSpring(@PreProcess(clazz = PreProcessorBySpringImpl.class) ObjectToProcessBySpring objectToPreProcessBySpring) {
        if (!objectToPreProcessBySpring.getHasBeenProcess()) {
            throw new IllegalStateException("Le preProcess ne s'est pas executé correctement.");
        }
    }

    @PreProcessing
    public void noPreProcessingByClazzAndContextSpring(@PreProcess(clazz = PreProcessorImpl.class) ObjectToProcess objectToPreProcess) {
        if (objectToPreProcess.getHasBeenProcess()) {
            throw new IllegalStateException("Le preProcess s'est executé correctement.");
        }
    }

    @PostProcessing(packageToScan = "santeclair.lunar.framework.aspect.processor")
    public ObjectToProcessBySpring postProcessingByPackageBySpring() {
        return new ObjectToProcessBySpring();
    }

    @PostProcessing(packageToScan = "santeclair.lunar.framework.aspect.processor")
    public ObjectToProcess noPostProcessingByPackageBySpring() {
        return new ObjectToProcess();
    }
}

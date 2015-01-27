package santeclair.lunar.framework.aspect.processor;

import santeclair.lunar.framework.aspect.processor.pre.PreProcessor;
import santeclair.lunar.framework.aspect.processor.pre.PreProcessorInterface;

@PreProcessor
public class PreProcessorImpl implements PreProcessorInterface<ObjectToProcess> {

    public void preProcess(ObjectToProcess param) {
        param.setHasBeenProcess(true);
    }

}

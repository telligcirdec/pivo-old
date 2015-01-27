package santeclair.lunar.framework.aspect.processor;

import org.springframework.stereotype.Component;

import santeclair.lunar.framework.aspect.processor.pre.PreProcessor;
import santeclair.lunar.framework.aspect.processor.pre.PreProcessorInterface;

@PreProcessor
@Component
public class PreProcessorBySpringImpl implements PreProcessorInterface<ObjectToProcessBySpring> {

    public void preProcess(ObjectToProcessBySpring param) {
        param.setHasBeenProcess(true);
    }

}

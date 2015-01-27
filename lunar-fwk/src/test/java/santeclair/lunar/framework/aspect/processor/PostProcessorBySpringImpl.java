package santeclair.lunar.framework.aspect.processor;

import org.springframework.stereotype.Component;

import santeclair.lunar.framework.aspect.processor.post.PostProcessor;
import santeclair.lunar.framework.aspect.processor.post.PostProcessorInterface;

@PostProcessor
@Component
public class PostProcessorBySpringImpl implements PostProcessorInterface<ObjectToProcessBySpring> {

    public void postProcess(ObjectToProcessBySpring retour) {
        retour.setHasBeenProcess(true);
    }

}

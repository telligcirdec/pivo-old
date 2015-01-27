package santeclair.lunar.framework.aspect.processor;

import santeclair.lunar.framework.aspect.processor.post.PostProcessor;
import santeclair.lunar.framework.aspect.processor.post.PostProcessorInterface;

@PostProcessor
public class PostProcessorImpl implements PostProcessorInterface<ObjectToProcess> {

    public void postProcess(ObjectToProcess retour) {
        retour.setHasBeenProcess(true);
    }

}

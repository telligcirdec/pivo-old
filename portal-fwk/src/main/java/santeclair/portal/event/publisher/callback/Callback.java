package santeclair.portal.event.publisher.callback;

public @interface Callback {

    public String topic();

    public String filter() default "";

}

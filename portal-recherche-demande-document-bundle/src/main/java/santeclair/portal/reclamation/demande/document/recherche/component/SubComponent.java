package santeclair.portal.reclamation.demande.document.recherche.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SubComponent {

    public int displayOrder() default Integer.MAX_VALUE;

}

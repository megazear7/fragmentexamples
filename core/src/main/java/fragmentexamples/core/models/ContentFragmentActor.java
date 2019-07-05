package fragmentexamples.core.models;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.FragmentTemplate;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.commons.lang.StringUtils;
import java.util.Optional;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContentFragmentActor {
    public static final String MODEL_TITLE = "Actor";

    @Inject @Self
    private Resource resource;

    private Optional<ContentFragment> contentFragment;

    @PostConstruct
    public void init() {
        contentFragment = Optional.ofNullable(resource.adaptTo(ContentFragment.class));
    }

    public String getModelTitle() {
        return contentFragment
                .map(ContentFragment::getTemplate)
                .map(FragmentTemplate::getTitle)
                .orElse(StringUtils.EMPTY);
    }

    public String getName() {
        return contentFragment
                .map(cf -> cf.getElement("name"))
                .map(ContentElement::getContent)
                .orElse(StringUtils.EMPTY);
    }
}

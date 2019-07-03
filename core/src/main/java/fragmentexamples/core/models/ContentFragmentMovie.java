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
public class ContentFragmentMovie {
	public static final String MODEL_TITLE = "Movie";

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

    public String getTitle() {
	    return contentFragment
				.map(cf -> cf.getElement("title"))
				.map(ContentElement::getContent)
				.orElse(StringUtils.EMPTY);
	}

    public String getDescription() {
	    return contentFragment
				.map(cf -> cf.getElement("description"))
				.map(ContentElement::getContent)
				.orElse(StringUtils.EMPTY);
	}

	public String getReleaseDate() {
	    return contentFragment
				.map(cf -> cf.getElement("releaseDate"))
				.map(ContentElement::getContent)
				.orElse(StringUtils.EMPTY);
	}

    public String getImdbProfile() {
        return contentFragment
                .map(cf -> cf.getElement("imdbProfile"))
                .map(ContentElement::getContent)
                .orElse(StringUtils.EMPTY);

    }

	public String getImage() {
	    return contentFragment
				.map(cf -> cf.getElement("image"))
				.map(ContentElement::getContent)
				.orElse(StringUtils.EMPTY);
	}
}

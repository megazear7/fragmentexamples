package fragmentexamples.core.models;

import com.day.cq.tagging.TagManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import java.util.ArrayList;
import java.util.List;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MovieListModel {
    @SlingObject
    private Resource currentResource;

    @SlingObject
    private ResourceResolver resourceResolver;

    @Inject @Named("cq:tags")
    private String[] cqTags;

    @Inject @Default(values = "/content/dam")
    private String parentPath;

    private final List<ContentFragmentMovie> movies = new ArrayList<>();

    private String message;

    @PostConstruct
    protected void init() {
        final TagManager tagManager = resourceResolver.adaptTo(TagManager.class);

        if (cqTags != null) {
            tagManager.find(parentPath, cqTags, true).forEachRemaining(resource -> {
                // The cq:tags property of the default ContentFragment tags field places the property on the
                // <content-fragment>/jcr:content/metadata node. And so to adapt the <content-fragment> you have to go two nodes upwards.
                final ContentFragmentMovie cfMovie = resource.getParent().getParent().adaptTo(ContentFragmentMovie.class);
                if (cfMovie != null && ContentFragmentMovie.MODEL_TITLE.equals(cfMovie.getModelTitle())) {
                    movies.add(cfMovie);
                }
            });
        }
    }

    public List<ContentFragmentMovie> getMovies() {
        return movies;
    }
}

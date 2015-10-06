package restbucks.bdd;

import java.util.Arrays;
import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.failures.PassingUponPendingStep;
import org.jbehave.core.failures.SilentlyAbsorbingFailure;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;


public class AcceptanceTests extends JUnitStories {

  public AcceptanceTests() {
    configuredEmbedder().embedderControls()
        .doGenerateViewAfterStories(true)
        .doIgnoreFailureInStories(true)
        .doIgnoreFailureInView(true)
        .doVerboseFailures(true)
        .doVerboseFiltering(true)
        ;
  }

  @Override
  protected List<String> storyPaths() {
    return new StoryFinder().findPaths("src/test/resources", "stories/*.story", "");
  }

  @Override
  public Configuration configuration() {
    return new MostUsefulConfiguration()
        .useStoryReporterBuilder(newStoryBuilder())
        .usePendingStepStrategy(new PassingUponPendingStep())
        .useFailureStrategy(new SilentlyAbsorbingFailure())
        .useStoryLoader(new LoadFromClasspath());
  }

  private StoryReporterBuilder newStoryBuilder() {
    return new StoryReporterBuilder()
        .withFailureTrace(true)
        .withFailureTraceCompression(true)
        .withFormats(Format.HTML, Format.XML);
  }

  @Override
  public InjectableStepsFactory stepsFactory() {
    return new InstanceStepsFactory(configuration(), getStepsObjects());
  }

  private List<?> getStepsObjects() {
    return Arrays.asList(new RestbuckSteps());
  }

}

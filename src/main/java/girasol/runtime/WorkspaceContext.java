package girasol.runtime;

public class WorkspaceContext extends Context {
	
	public WorkspaceContext(Workspace workspace)
	{
		super(Application.getApplication().getContext());
		this.workspace = workspace;
	}
	
}

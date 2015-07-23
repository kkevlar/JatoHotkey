import java.util.ArrayList;

public class Event
{
	private ArrayList<Input> inputs;
	private InputKeeperHook hook;
	private boolean didTrigger;
	
	public Event(InputKeeperHook hook)
	{
		this.inputs = new ArrayList<Input>();
		this.hook = hook;
	}
	public Input addTo(Input input)
	{
		int index = this.getInputs().indexOf(input);
		if(index >= 0)
			return this.getInputs().get(index);
		Input nInput = hook.addTo(input);
		this.getInputs().add(nInput);
		return nInput;
	}

	private ArrayList<Input> getInputs() { return inputs; }
	private void setInputs(ArrayList<Input> inputs) { this.inputs = inputs; }

	public boolean isTriggered()
	{
		if(this.getInputs().isEmpty())
			return false;
		for(int i = 0; i < this.getInputs().size(); i++)
		{
			if(!(this.getInputs().get(i).isDown()))
				return false;
		}
		return true;
	}
	public boolean shouldTriggerAction()
	{
		boolean triggered = isTriggered();
		if((!(didTrigger)) && triggered)
		{
			didTrigger = true;
			return true;
		}
		if(!triggered)
		{
			didTrigger = false;
		}
		return false;
	}	
	
	@Override
	public String toString() { return makeParseable(); }
		/*
		return "Event [inputs=" + inputs + ", didTrigger=" + didTrigger + ", isTriggered()=" + isTriggered()
				+ ", shouldTriggerAction()=" + shouldTriggerAction() + "]";

		 */
	public String makeParseable()
	{
		String line = "";
		for(int i = 0; i < this.getInputs().size(); i++)
		{
			line += this.getInputs().get(i).getAsParsableString() + "&";
		}
		line = line.substring(0,line.length()-1);
		return line;
	}
}

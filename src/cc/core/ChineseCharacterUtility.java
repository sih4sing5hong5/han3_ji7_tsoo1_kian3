package cc.core;

public class ChineseCharacterUtility
{
	private CharSequence sequence;
	private int start;

	public ChineseCharacterUtility(CharSequence sequence)
	{
		this.sequence = sequence;
		this.start = 0;
	}

	public ChineseCharacter parseTree()
	{
		ChineseCharacter chineseCharacter = null;
		if (Character.isHighSurrogate(sequence.charAt(start)))
		{
			if (start + 1 < sequence.length())
			{
				chineseCharacter = new ChineseCharacterBase(
						Character.toCodePoint(sequence.charAt(start),
								sequence.charAt(start + 1)));
				start += 2;
			}
			else
				start++;
		}
		else
		{
			chineseCharacter=new ChineseCharacterCombination();
		}
	}

}

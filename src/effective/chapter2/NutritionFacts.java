/**
 * 
 */
package effective.chapter2;

/**
 * 第二条：遇到多个构造器参数时要考虑使用构建器（builder，链式风格）
 * 创建时间 ： 2017年5月11日下午3:20:26
 */
public class NutritionFacts {
	private final int servingSize;
	private final int servings;
	private final int calories;
	private final int fat;
	private final int sodium;
	private final int carbohydrate;
	
	public static class Builder{
		//必须变量
		private final int servingSize;
		private final int servings;
		//非必须变量,设置默认值
		private int calories=0;
		private int fat=0;
		private int sodium=0;
		private int carbohydrate=0;
		
		public Builder(int servingSize,int servings){
			this.servings=servings;
			this.servingSize=servingSize;
		}
		
		public Builder calories(int val){
			calories=val;
			return this;
		}
		
		public Builder fat(int val){
			fat=val;
			return this;
		}
		
		public Builder sodium(int val){
			sodium=val;
			return this;
		}
		
		public Builder carbohydrate(int val){
			carbohydrate=val;
			return this;
		}
		
		public NutritionFacts build(){
			return new NutritionFacts(this);
		}
	}
	
	private NutritionFacts(Builder builder){
		servingSize=builder.servingSize;
		servings=builder.servings;
		calories=builder.calories;
		fat=builder.fat;
		sodium=builder.sodium;
		carbohydrate=builder.carbohydrate;
	}
	
	
}

package systems.drawSystems;


class YInfo {
	public float xMin;
	public float zxMin;

	public float xMax;
	public float zxMax;

	public float either;

//	public float[] zValues = new float[TestFrame.WIDTH];
	

	public YInfo() {
		// TODO Auto-generated constructor stub
	}
	public YInfo(float xMin, float z1, float xMax, float z2) {
		if(xMin > xMax) {
			this.xMin = xMax;
			this.zxMin = z2;
			
			this.xMax = xMin;
			this.zxMax = z1;
			
		}else {
			this.xMin = xMin;
			this.zxMin = z1;
			
			this.xMax = xMax;
			this.zxMax = z2;
		}
		
		

	}
}
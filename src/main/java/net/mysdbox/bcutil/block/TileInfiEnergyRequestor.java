package net.mysdbox.bcutil.block;

import java.util.ArrayList;
import java.util.Stack;

import buildcraft.api.tiles.IControllable.Mode;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile.PipeType;
import buildcraft.core.lib.RFBattery;
import buildcraft.core.lib.block.TileBuildCraft;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.mysdbox.bcutil.lib.LoggingUtil;
import buildcraft.energy.TileEngineStone;
import net.mysdbox.bcutil.BuildCraftUtilities;
import net.mysdbox.bcutil.lib.CodingUtil;

public class TileInfiEnergyRequestor extends TileBuildCraft
	implements IEnergyHandler, IEnergyConnection, IPipeConnection {
	private static final int STORAGE = 500;
	private Stack<Integer> InputEnergyStatistics = new Stack<Integer>();
	private Stack<Integer> OutputEnergyStatistics = new Stack<Integer>();
	private ArrayList<ForgeDirection> OutputDirection = new ArrayList<ForgeDirection>();
	private ArrayList<ForgeDirection> InputDirection = new ArrayList<ForgeDirection>();
	
	private Block[] blocks = new Block[6]; 
	private long lastEnergyRecived = 0;
	
	private int outcount = 0;
	private int incount = 0;
	private RFBattery battery;
	public TileInfiEnergyRequestor() {
		super();
		setBattery(battery);
	}
	
	@Override
	public void initialize(){
		super.initialize();
		setControlMode(Mode.On);
	}
	
	@Override
	public int receiveEnergy(ForgeDirection direction, int energy, boolean simulate){
		if(OutputDirection.contains(direction)) return 0;
		if(!InputDirection.contains(direction)){
			incount++;
			InputEnergyStatistics.setSize(incount);
			InputDirection.add(direction);
			BuildCraftUtilities.sendChatMessage("A new input detected!", "gray");
			InputEnergyStatistics.push(energy);
		}else{
			if(lastEnergyRecived == worldObj.getTotalWorldTime()){
				return energy;
			}
			InputEnergyStatistics.push(energy);
		}
		return energy;
	}
	
	@Override
	public int extractEnergy(ForgeDirection direction, int energy, boolean simulate){
		if(InputDirection.contains(direction)) return 0;
		if(!OutputDirection.contains(direction)){
			outcount++;
			OutputEnergyStatistics.setSize(outcount);
			OutputDirection.add(direction);
			BuildCraftUtilities.sendChatMessage("A new output detected!", "gray");
			OutputEnergyStatistics.push(energy);
		}else{
			if(lastEnergyRecived == worldObj.getTotalWorldTime()){
				return energy;
			}
			OutputEnergyStatistics.push(energy);
		}
		return energy;
	}
	
	@Override
	public int getEnergyStored(ForgeDirection paramForgeDirection){
		return STORAGE;
	}
	
	@Override
	public int getMaxEnergyStored(ForgeDirection paramForgeDirection) {
		return STORAGE * 2;
	}
	
	@Override
	public boolean canConnectEnergy(ForgeDirection paramForgeDirection){
		return true;
	}
	
	public void reset(){
		outcount = 0;
		incount = 0;
	}
	
	public int getAverageOutput(){
		if(OutputEnergyStatistics.isEmpty() || outcount == 0) return 0;
		Object[] temp = OutputEnergyStatistics.toArray();
		int tmp = 0;
		for(int i = 0; i < temp.length; i++){
			try{
				tmp += (Integer)temp[i];
			}catch(NullPointerException e){
				break;
			}
		}
		LoggingUtil.log(temp);
		return tmp / (OutputEnergyStatistics.size() / outcount);
	}
	
	public int getAverageInput(){
		if(InputEnergyStatistics.isEmpty() || incount == 0) return 0;
		Object[] temp = InputEnergyStatistics.toArray();
		int tmp = 0;
		for(int i = 0; i < temp.length; i++){
			try{
				tmp += (Integer)temp[i];
			}catch(NullPointerException e){
				break;
			}
		}
		LoggingUtil.log(temp);
		return tmp / (temp.length / incount);
	}
	
	public void onBlockUpdate(){
		//TODO write your code to do something...
		//reset();
		BuildCraftUtilities.sendChatMessage("A block updation detected!", "gray");
	}
	
	public void onNeighborChange(ForgeDirection side){
		int[] coords = ForgeDirectionToRealCoords(side);
		Block b = worldObj.getBlock(xCoord + coords[0], yCoord + coords[1], zCoord + coords[2]);
		TileEntity ent
			= worldObj.getTileEntity(xCoord + coords[0], yCoord + coords[1], zCoord + coords[2]);
		blocks[side.ordinal()] = b;
		if(OutputDirection.contains(side)){
			OutputDirection.remove(side);
			outcount--;
			BuildCraftUtilities.sendChatMessage("A output has been removed", "gray");
		}else if(InputDirection.contains(side)){
			InputDirection.remove(side);
			incount--;
			BuildCraftUtilities.sendChatMessage("A input has been removed", "gray");
		}
		if(ent == null) return;
		
	}
	
	@Override
	public void updateEntity(){
		/*boolean trigger = false;
		for(Object dir : OutputDirection.toArray()){
			ForgeDirection d = (ForgeDirection)dir;
			int[] coords = ForgeDirectionToRealCoords(d);
			Block b = worldObj.getBlock(xCoord + coords[0], yCoord + coords[1], zCoord + coords[2]);
			if(blocks[d.ordinal()] == null){
				blocks[d.ordinal()] = b;
				trigger = true;
				continue;
			}
			if(blocks[d.ordinal()] == b) continue;
			blocks[d.ordinal()] = b;
			OutputDirection.remove(d);
			outcount--;
			trigger = true;
		}
		for(Object dir : InputDirection.toArray()){
			ForgeDirection d = (ForgeDirection)dir;
			int[] coords = ForgeDirectionToRealCoords(d);
			Block b = worldObj.getBlock(xCoord + coords[0], yCoord + coords[1], zCoord + coords[2]);
			if(blocks[d.ordinal()] == null){
				blocks[d.ordinal()] = b;
				trigger = true;
				continue;
			}
			if(blocks[d.ordinal()] == b) continue;
			blocks[d.ordinal()] = b;
			InputDirection.remove(d);
			incount--;
			trigger = true;
		}
		if(trigger){
			onBlockUpdate();
		}*/
		if(outcount != OutputEnergyStatistics.size()){
			OutputEnergyStatistics.setSize(outcount);
			OutputEnergyStatistics.clear();
		}
		if(incount != InputEnergyStatistics.size()){
			InputEnergyStatistics.setSize(incount);
			InputEnergyStatistics.clear();
		}
	}
	
	@Override
	public ConnectOverride overridePipeConnection(PipeType paramPipeType, ForgeDirection paramForgeDirection) {
		if(paramPipeType == PipeType.POWER) return ConnectOverride.CONNECT;
		return ConnectOverride.DISCONNECT;
	}
	
	public int[] ForgeDirectionToRealCoords(ForgeDirection dir){
		switch (dir) {
		case UP:
			return new int[] {0, 1, 0};
		case DOWN:
			return new int[] {0, 1, 0};
		case WEST:
			return new int[] {-1, 0, 0};
		case EAST:
			return new int[] {1, 0, 0};
		case NORTH:
			return new int[] {0, 0, -1};
		case SOUTH:
			return new int[] {0, 0, 1};
		default:
			break;
		}
		return new int[] {0, 0, 0};
	}
}

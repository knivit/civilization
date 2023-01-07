--
--	FILE:	 FourContinentsWorld.lua
--	AUTHORS:  Brian Wade and Shaun Seckman
--	PURPOSE: FourContinent variant of Fractal World
------------------------------------------------------------------------------
--	Copyright (c) 2009 Firaxis Games, Inc. All rights reserved.
------------------------------------------------------------------------------
include("FractalWorld");

FourContinent = {};

function ellipseCheck(x, y, centerX, centerY, majorAxisSquared, minorAxisSquared)
	local deltaX = x-centerX;
	local deltaY = y-centerY;
	local deltaXSquared = deltaX * deltaX;
	local deltaYSquared = deltaY * deltaY;
	local d = deltaXSquared/majorAxisSquared + deltaYSquared/minorAxisSquared;
	if d <= 1 then
		return true;
	else
		return false;
	end
end

-------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------
function FourContinent.Create(fracXExp, fracYExp)
	
	local gridWidth, gridHeight = Map.GetGridSize();
	
	local sea_level = GameInfo.SeaLevels[Map.GetSeaLevel()];
	
	local data = {
		InitFractal = FractalWorld.InitFractal,
		CheckForOverrideDefaultUserInputVariances = FractalWorld.CheckForOverrideDefaultUserInputVariances,
		ShiftPlotTypes = FractalWorld.ShiftPlotTypes,
		ShiftPlotTypesBy = FractalWorld.ShiftPlotTypesBy,
		DetermineXShift = FractalWorld.DetermineXShift,
		DetermineYShift = FractalWorld.DetermineYShift,
		GenerateCenterRift = FractalWorld.GenerateCenterRift,
		GeneratePlotTypes = FourContinent.GeneratePlotTypes,
		
		iFlags = Map.GetFractalFlags(),
		
		fracXExp = fracXExp,
		fracYExp = fracYExp,
		
		iNumPlotsX = gridWidth,
		iNumPlotsY = gridHeight,
		plotTypes = table.fill(PlotTypes.PLOT_OCEAN, gridWidth * gridHeight),
	
		seaLevelChange = sea_level.SeaLevelChange,
		seaLevelMax = 100,
		seaLevelMin = 0,
		stripRadius = 15,
		
	};
		
	return data;
end	
-------------------------------------------------------------------------------------------
function FourContinent:GeneratePlotTypes(args)
	if(args == nil) then args = {}; end
	
	local water_percent = args.water_percent or 78;
	local shift_plot_types = args.shift_plot_types or true;
	local grain_amount = args.grain_amount or 3;
	
	-- Check for changes to User Input variances.
	--self.checkForOverrideDefaultUserInputVariances();
	local WorldSizeTypes = {};
	for row in GameInfo.Worlds() do
		WorldSizeTypes[row.Type] = row.ID;
	end
	
	-- Hills and Mountains handled differently according to map size.
	local sizekey = Map.GetWorldSize();
	local sizevalues = {
		[WorldSizeTypes.WORLDSIZE_DUEL]     = 3,
		[WorldSizeTypes.WORLDSIZE_TINY]     = 3,
		[WorldSizeTypes.WORLDSIZE_SMALL]    = 4,
		[WorldSizeTypes.WORLDSIZE_STANDARD] = 4,
		[WorldSizeTypes.WORLDSIZE_LARGE]    = 5,
		[WorldSizeTypes.WORLDSIZE_HUGE]		= 5
	};
	local grain = sizevalues[sizekey] or 3;
	
	local platevalues = {
		[WorldSizeTypes.WORLDSIZE_DUEL]		= 6,
		[WorldSizeTypes.WORLDSIZE_TINY]     = 9,
		[WorldSizeTypes.WORLDSIZE_SMALL]    = 12,
		[WorldSizeTypes.WORLDSIZE_STANDARD] = 18,
		[WorldSizeTypes.WORLDSIZE_LARGE]    = 24,
		[WorldSizeTypes.WORLDSIZE_HUGE]     = 30
	};
	local numPlates = platevalues[sizekey] or 5;

	self.hillsFrac = Fractal.Create(self.iNumPlotsX, self.iNumPlotsY, grain, self.iFlags, self.fracXExp, self.fracYExp);
	-- have the hills be clumpy with a bit of ridges in them
	self.hillsFrac:BuildRidges(numPlates, {FRAC_WRAP_X = true}, 1, 2);
	
	self.mountainsFrac = Fractal.Create(self.iNumPlotsX, self.iNumPlotsY, 4, self.iFlags, self.fracXExp, self.fracYExp);
	-- have the mountain ranges tend to be be distinct
	self.mountainsFrac:BuildRidges((numPlates * 2) / 3, {}, 6, 1);

	water_percent = water_percent + self.seaLevelChange;
	water_percent = math.clamp(water_percent, self.seaLevelMin, self.seaLevelMax);
		
	local iWaterThreshold = self.continentsFrac:GetHeight(water_percent);
	local iHillsBottom1 = self.hillsFrac:GetHeight(25);
	local iHillsTop1 = self.hillsFrac:GetHeight(31);
	local iHillsBottom2 = self.hillsFrac:GetHeight(69);
	local iHillsTop2 = self.hillsFrac:GetHeight(75);
	local iHillsClumps = self.mountainsFrac:GetHeight(4);
	local iHillsNearMountains = self.mountainsFrac:GetHeight(85);
	local iMountainThreshold = self.mountainsFrac:GetHeight(94);
	local iPassThreshold = self.hillsFrac:GetHeight(85);
	local iMountain100 = self.mountainsFrac:GetHeight(100);
	local iMountain99 = self.mountainsFrac:GetHeight(99);
	local iMountain98 = self.mountainsFrac:GetHeight(98);
	local iMountain97 = self.mountainsFrac:GetHeight(97);
	local iMountain95 = self.mountainsFrac:GetHeight(95);

	local centerX0 = self.iNumPlotsX * 0.25;
	local centerY0 = self.iNumPlotsY * 0.25;
	local centerX1 = self.iNumPlotsX * 0.25;
	local centerY1 = self.iNumPlotsY * 0.75;
	local centerX2 = self.iNumPlotsX * 0.75;
	local centerY2 = self.iNumPlotsY * 0.25;
	local centerX3 = self.iNumPlotsX * 0.75;
	local centerY3 = self.iNumPlotsY * 0.75;

	-- major and minor axes
	local majorAxis = centerX0 * 0.65;
	local minorAxis = centerY0 * 0.65;
	local majorAxisSquared = majorAxis * majorAxis;
	local minorAxisSquared = minorAxis * minorAxis;
	
	for x = 0, self.iNumPlotsX - 1, 1 do
		for y = 0, self.iNumPlotsY - 1, 1 do
			local i = y * self.iNumPlotsX + x;
			local val = self.continentsFrac:GetHeight(x, y);
			
			-- let's blend ellipses in to form the basic continents
			local h = iWaterThreshold - 0.2 * iWaterThreshold;
			if ellipseCheck(x, y, centerX0, centerY0, majorAxisSquared, minorAxisSquared) then
				h = iWaterThreshold + 0.2 * iWaterThreshold;
			elseif ellipseCheck(x, y, centerX1, centerY1, majorAxisSquared, minorAxisSquared) then
				h = iWaterThreshold + 0.2 * iWaterThreshold;
			elseif ellipseCheck(x, y, centerX2, centerY2, majorAxisSquared, minorAxisSquared) then
				h = iWaterThreshold + 0.2 * iWaterThreshold;
			elseif ellipseCheck(x, y, centerX3, centerY3, majorAxisSquared, minorAxisSquared) then
				h = iWaterThreshold + 0.2 * iWaterThreshold;
			end
						
			val = (val + h + h) * 0.33;
			
			local mountainVal = self.mountainsFrac:GetHeight(x, y);
			local hillVal = self.hillsFrac:GetHeight(x, y);
	
			if(val <= iWaterThreshold) then
				self.plotTypes[i] = PlotTypes.PLOT_OCEAN;
				
				-- on second thought build some islands near the ridge lines
				if (mountainVal == iMountain100) then
					self.plotTypes[i] = PlotTypes.PLOT_MOUNTAIN; -- this would be especially cool if there were a special volcano tile
				elseif (mountainVal == iMountain99) then
					self.plotTypes[i] = PlotTypes.PLOT_HILLS;
				elseif (mountainVal == iMountain97) then
					self.plotTypes[i] = PlotTypes.PLOT_LAND;
				elseif (mountainVal == iMountain95) then
					self.plotTypes[i] = PlotTypes.PLOT_LAND;
				end
					
			else
				if (mountainVal >= iMountainThreshold) then
					-- use the other ridged fractal to put passes through the ranges (sometimes, it is random after all)
					if (hillVal >= iPassThreshold) then
						self.plotTypes[i] = PlotTypes.PLOT_HILLS;
					else
						self.plotTypes[i] = PlotTypes.PLOT_MOUNTAIN;
					end
				elseif (mountainVal >= iHillsNearMountains) then
					self.plotTypes[i] = PlotTypes.PLOT_HILLS; -- foot hills
				else
					if ((hillVal >= iHillsBottom1 and hillVal <= iHillsTop1) or (hillVal >= iHillsBottom2 and hillVal <= iHillsTop2)) then
						self.plotTypes[i] = PlotTypes.PLOT_HILLS;
					else
						self.plotTypes[i] = PlotTypes.PLOT_LAND;
					end
				end
			end
		end
	end

	if(shift_plot_types) then
		self:ShiftPlotTypes();
	end

	return self.plotTypes;
end

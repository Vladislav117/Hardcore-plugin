package serv;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.type.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.plugin.Plugin;

import mindustry.type.*;
import mindustry.entities.type.*;
import static mindustry.Vars.*;
import mindustry.game.Team;
import mindustry.game.EventType;
import mindustry.core.GameState;

public class Serv extends Plugin{
	public int real_ticks = -3600;
	public int current_wave = 0;
	
	public String[] units_easy_category = {"wraith", "dagger", "crawler"};
	public String[] units_medium_category = {"ghoul",  "titan"};
	public String[] units_hard_category = {"revenant",  "fortress"};
	public String[] units_insane_category = {"lich", "reaper", "eruptor", "eradicator"};
	public String difficulty = "easy";
	public float difficulty_factor = 1.0f;
	public float difficulty_factor_grow = 1.06f;
	public int second_per_wave = 30;
	
	public int wave_loc_easy = 0;
	public int wave_loc_medium = 12;
	public int wave_loc_hard = 30;
	public int wave_loc_insane = 50;

	public int next_machine_id = 0;
	
    public Serv(){
		/*
        Events.on(BuildSelectEvent.class, event -> {
            if(!event.breaking && event.builder != null && event.builder.buildRequest() != null && event.builder.buildRequest().block == Blocks.thoriumReactor && event.builder instanceof Player){
                Call.sendMessage("[scarlet]ALERT![] " + ((Player)event.builder).name + " has begun building a reactor at " + event.tile.x + ", " + event.tile.y);
            }
        });
		*/
		Events.on(EventType.GameOverEvent.class, event -> {
			difficulty = "easy";
			difficulty_factor = 1.0f;
			real_ticks = -3600;
			current_wave = 0;
		});
		
		
		Events.on(EventType.Trigger.class, event -> {
			if(Vars.state.is(GameState.State.playing))
			{
				if (real_ticks >= second_per_wave*60)
				{
					difficulty_factor = difficulty_factor * difficulty_factor_grow;
					current_wave++;
					
					if (current_wave >= wave_loc_medium && current_wave < wave_loc_hard && difficulty != "medium") {difficulty = "medium"; Call.onInfoMessage("Вы достигли сложности '[yellow]СРЕДНЯЯ[]'");}
					if (current_wave >= wave_loc_hard && current_wave < wave_loc_insane && difficulty != "hard") {difficulty = "hard"; Call.onInfoMessage("Вы достигли сложности '[red]СЛОЖНАЯ[]'");}
					if (current_wave >= wave_loc_insane && difficulty != "insane") {difficulty = "insane"; Call.onInfoMessage("Вы достигли сложности '[purple]НЕВЫНОСИМАЯ[]'");}

					
					boolean normal_pos = false;
					float x_pos = 0.0f;
					float y_pos = 0.0f;
					
					
					//x_pos = (float)Math.random()*(float)Vars.world.width()*8.0f;
					//y_pos = (float)Math.random()*(float)Vars.world.height()*8.0f;
					
					int cur_x = 0;
					int cur_y = 0;
					while (!normal_pos)
					{
						cur_x = (int)((float)Math.random()*(float)Vars.world.width());
						cur_y = (int)((float)Math.random()*(float)Vars.world.height());
						if (Vars.world.tile(cur_x, cur_y).block() == Blocks.air)
						{
							normal_pos = true;
							x_pos = (float)cur_x*8.0f;
							y_pos = (float)cur_y*8.0f;
						}
					}
					if (difficulty == "easy" || difficulty == "medium" || difficulty == "hard" || difficulty == "insane"){
						for (int cnt=0; cnt < Math.floor(difficulty_factor); cnt++)
						{
							next_machine_id = (int)Math.floor(Math.random() * units_easy_category.length);
							UnitType unit = content.units().find(u -> u.name.equals(units_easy_category[next_machine_id]));
							BaseUnit baseUnit = unit.create(Team.crux);
							baseUnit.set(x_pos, y_pos);
							baseUnit.add();
						}
					}
					if (difficulty == "medium" || difficulty == "hard" || difficulty == "insane"){
						for (int cnt=0; cnt < Math.floor(difficulty_factor/2f); cnt++)
						{
							next_machine_id = (int)Math.floor(Math.random() * units_medium_category.length);
							UnitType unit = content.units().find(u -> u.name.equals(units_medium_category[next_machine_id]));
							BaseUnit baseUnit = unit.create(Team.crux);
							baseUnit.set(x_pos, y_pos);
							baseUnit.add();
						}
					}
					if (difficulty == "hard" || difficulty == "insane"){
						for (int cnt=0; cnt < Math.floor(difficulty_factor/5f); cnt++)
						{
							next_machine_id = (int)Math.floor(Math.random() * units_hard_category.length);
							UnitType unit = content.units().find(u -> u.name.equals(units_hard_category[next_machine_id]));
							BaseUnit baseUnit = unit.create(Team.crux);
							baseUnit.set(x_pos, y_pos);
							baseUnit.add();
						}
					}
					if (difficulty == "insane"){
						for (int cnt=0; cnt < Math.floor(difficulty_factor/18f); cnt++)
						{
							next_machine_id = (int)Math.floor(Math.random() * units_insane_category.length);
							UnitType unit = content.units().find(u -> u.name.equals(units_insane_category[next_machine_id]));
							BaseUnit baseUnit = unit.create(Team.crux);
							baseUnit.set(x_pos, y_pos);
							baseUnit.add();
						}
					}
					real_ticks = 0;
				}
				real_ticks++;
			};
		});
		
    }
	
    @Override
    public void registerServerCommands(CommandHandler handler){
        handler.register("addtoloc1", "Add 1 to local difficulty", args -> {
			difficulty_factor = difficulty_factor + 1f;
        });
		handler.register("addtoloc2", "Add 2 to local difficulty", args -> {
			difficulty_factor = difficulty_factor + 2f;
        });
		handler.register("addtoloc4", "Add 4 to local difficulty", args -> {
			difficulty_factor = difficulty_factor + 4f;
        });
		handler.register("addtoloc8", "Add 8 to local difficulty", args -> {
			difficulty_factor = difficulty_factor + 8f;
        });
		handler.register("addtoloc16", "Add 16 to local difficulty", args -> {
			difficulty_factor = difficulty_factor + 16f;
        });
		handler.register("addtoloc32", "Add 32 to local difficulty", args -> {
			difficulty_factor = difficulty_factor + 32f;
        });
		handler.register("addtoloc64", "Add 64 to local difficulty", args -> {
			difficulty_factor = difficulty_factor + 64f;
        });

		handler.register("addtowave1", "Add 1 to wave", args -> {
			current_wave = current_wave + 1;
        });
		handler.register("addtowave5", "Add 5 to wave", args -> {
			current_wave = current_wave + 5;
        });
		handler.register("addtowave10", "Add 10 to wave", args -> {
			current_wave = current_wave + 10;
        });
		handler.register("addtowave50", "Add 50 to wave", args -> {
			current_wave = current_wave + 50;
        });
		handler.register("addtowave100", "Add 100 to wave", args -> {
			current_wave = current_wave + 100;
        });
    }
	

    //register commands that player can invoke in-game
    @Override
    public void registerClientCommands(CommandHandler handler){
		handler.<Player>register("difficulty", "Показывает сложность.", (args, player) -> {
			if (difficulty == "easy"){player.sendMessage("Сложность: [green]ЛЁГКАЯ[]");}
			if (difficulty == "medium"){player.sendMessage("Сложность: [yellow]СРЕДНЯЯ[]");}
			if (difficulty == "hard"){player.sendMessage("Сложность: [red]СЛОЖНАЯ[]");}
			if (difficulty == "insane"){player.sendMessage("Сложность: [purple]НЕВЫНОСИМАЯ[]");}
        });
		handler.<Player>register("loc", "Показывает фактор сложности", (args, player) -> {
			player.sendMessage("Difficulty factor: "+new Double((double)difficulty_factor).toString());
        });
		handler.<Player>register("wave", "Показывает номер следующей волны.", (args, player) -> {
			player.sendMessage("Следующая волна № "+new Double((double)current_wave).toString());
        });
    }
}

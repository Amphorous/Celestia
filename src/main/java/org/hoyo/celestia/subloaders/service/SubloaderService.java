package org.hoyo.celestia.subloaders.service;

import org.hoyo.celestia.builds.BuildNodeRepository;
import org.hoyo.celestia.builds.UIDNodeRepository;
import org.hoyo.celestia.user.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SubloaderService {

    private final UIDNodeRepository uidNodeRepository;
    private final BuildNodeRepository buildNodeRepository;

    public SubloaderService(UIDNodeRepository uidNodeRepository, BuildNodeRepository buildNodeRepository) {
        this.uidNodeRepository = uidNodeRepository;
        this.buildNodeRepository = buildNodeRepository;
    }

    public Boolean userSubloader(User user){
        if(!user.getDetailInfo().getPrivacySettingInfo().getDisplayCollection()){
            //users builds are private, return false
            return false;
        }

        //merge uid node into db
        uidNodeRepository.createUIDNodeIfNotExists(user.getUid());

        ArrayList<AvatarDetail> avatarDetailList = user.getDetailInfo().getAvatarDetailList();
        for(AvatarDetail character : avatarDetailList){
            /*
            * check if build exists for this character, else jump to calc
            *   if build exists, then check level, talent level string, if not same, jump to calc
            *       if all relics are same, skip iteration
            *       else, jump to calc
            *
            * calc (FightProps object with all the stats added)
            * read level and use metaFile to get base stats
            * read equipment, use meta to get stats
            * read skilllist, use meta to get stats
            * read artifacts, append stats
            * */
            //check if same build with different levels/talents exists, if so, replace it with the new build, in this case, this iteration will proceed
            //see if the shown build already exists
            // to do this, check talent levels, level, artifact
            //make a build object for each character
            //-->
            ArrayList<Skill> skillListTree = character.getSkillTreeList();
            String characterSkillListString = "";
            for(Skill skill : skillListTree){
                characterSkillListString += String.valueOf(skill.getLevel());
            }
            Boolean shouldI = shouldICalulateAgain(character, user.getUid(), characterSkillListString);

        }
        //this marks the end of subloading, having read the user builds
        return true;
    }

    Boolean shouldICalulateAgain(AvatarDetail character, String uid, String characterSkillListString){

        //if the check breaks later for a character which is being read for the first time, uncomment this
//        if(!buildRepository.hasBuilds(uid, character.getAvatarId())){
//            return true;
//        }
        Integer level = character.getLevel();

        //characterSkillListString holds SkillListString for the current character build you're reading through
        //get level, create skillliststring, query neo for b:BuildNode {level: $level, skillliststing: $skillliststing}
        if(!buildNodeRepository.hasLevelsOnStaticBuild(uid, character.getAvatarId(), characterSkillListString, level)){
            return true;
        }

        return false;
    }

    String calculateRelicId(Relic relic){
        try {
            String relicId="";
            relicId+=relic.getTid();
            relicId+=relic.getMainAffixId()+"_";
            ArrayList<SubAffix> subAffixList = relic.getSubAffixList();
            for(SubAffix subAffix : subAffixList){
                relicId+=subAffix.getAffixId()+"-"+subAffix.getCnt()+"-"+subAffix.getStep()+"_";
            }
            return relicId;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Unable to calculate relic ID");
            return "INVALID";
        }
    }
}

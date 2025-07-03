package org.hoyo.celestia.subloaders.service;

import org.hoyo.celestia.builds.BuildNodeRepository;
import org.hoyo.celestia.uids.UIDNodeRepository;
import org.hoyo.celestia.relics.RelicNodeRepository;
import org.hoyo.celestia.relics.service.CreateRelicService;
import org.hoyo.celestia.user.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
public class SubloaderService {

    private final UIDNodeRepository uidNodeRepository;
    private final BuildNodeRepository buildNodeRepository;
    private final RelicNodeRepository relicNodeRepository;
    private final CreateRelicService createRelicService;

    public SubloaderService(UIDNodeRepository uidNodeRepository, BuildNodeRepository buildNodeRepository, RelicNodeRepository relicNodeRepository, CreateRelicService createRelicService) {
        this.uidNodeRepository = uidNodeRepository;
        this.buildNodeRepository = buildNodeRepository;
        this.relicNodeRepository = relicNodeRepository;
        this.createRelicService = createRelicService;
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
            if(shouldI){
                // look for build with character.avatarId and isStatic == true and go to its fightpropnode and detach delete it
                // look for build with character.avatarId and isStatic == true and detach delete it

                // create a buildnode (whose isStatic == true) with the existing information

                // create links from buildnode (whose isStatic == true) to corresponding relics

                // calculate fightpropnode and link to buildnode (whose isStatic == true)

                // link buildnode

            }

        }
        //this marks the end of subloading, having read the user builds
        return true;
    }

    public Boolean shouldICalulateAgain(AvatarDetail character, String uid, String characterSkillListString){

        //if the check breaks later for a character which is being read for the first time, uncomment this
//        if(!buildRepository.hasBuilds(uid, character.getAvatarId())){
//            return true;
//        }
        Boolean flag = false;
        Integer level = character.getLevel();

        //characterSkillListString holds SkillListString for the current character build you're reading through
        //get level, create skillliststring, query neo for b:BuildNode {level: $level, skillliststing: $skillliststing}
        if(!buildNodeRepository.hasLevelsOnStaticBuild(uid, character.getAvatarId(), characterSkillListString, level)){
            flag = true;
        }
        Set<String> staticNodeRelicIdSet = relicNodeRepository.getAllRelicIdsFromStaticNode(uid, character.getAvatarId(), true);
        Set<String> currentRelicIdSet = createRelicService.getRelicIdSetFromAvatarDetails(character);
        if(!staticNodeRelicIdSet.equals(currentRelicIdSet)){
            //check which relicIds are new among the set, then see if the "new" relicIds exist in DB, insert if they don't
            currentRelicIdSet.removeAll(staticNodeRelicIdSet);
            for(String relicId : currentRelicIdSet){
                if(!relicNodeRepository.existsRelic(uid, relicId)){
                    //this relic id is something that was made by us
                    Integer pos = ((int) relicId.charAt(0)) - 1;
                    //this is making a relic node at uid->relic-><here> with the substats dangling from it
                    //therefore when you want to link these to a build then you must search for uid->relics->thisrelic
                    createRelicService.createRelicNode(character.getRelicList().get(pos), uid, relicId);
                }
            }
            flag = true;
        }

        return flag;
    }

}

package innossh.aiwolf.demo;

import org.aiwolf.client.lib.ComingoutContentBuilder;
import org.aiwolf.client.lib.Content;
import org.aiwolf.client.lib.ContentBuilder;
import org.aiwolf.client.lib.DivinedResultContentBuilder;
import org.aiwolf.common.data.*;
import org.aiwolf.common.net.GameInfo;
import org.aiwolf.common.net.GameSetting;

import java.util.*;

public class DemoSeer implements Player {
    private Agent me;
    private GameInfo currentGameInfo;
    private Deque<Judge> myDivinationQueue = new LinkedList<>();
    private List<Agent> villagerList = new ArrayList<>();
    private List<Agent> werewolfList = new ArrayList<>();
    private List<Agent> grayList = new ArrayList<>();
    private boolean saidCo = false;
    private int talkListHead;

    private Map<Agent, Role> comingOutMap = new HashMap<>();
//    private List<Judge> divitionList = new ArrayList<>();

    @Override
    public String getName() {
        return "DemoSeer";
    }

    @Override
    public void update(GameInfo gameInfo) {
        currentGameInfo = gameInfo;
        for (int i = talkListHead; i < currentGameInfo.getTalkList().size(); i++) {
            Talk talk = currentGameInfo.getTalkList().get(i);
            Agent talker = talk.getAgent();
            if (talker == me) {
                continue;
            }
            Content content = new Content(talk.getText());
            switch (content.getTopic()) {
                case COMINGOUT:
                    comingOutMap.put(talker, content.getRole());
                    break;
                case DIVINED:
                    // TODO:
//                    Judge judge = new Judge(currentGameInfo.getDay(), talker, content.getTarget(), content.getResult());
//                    divitionList.add(judge);
                    break;
                case IDENTIFIED:
                    // TODO:
                    break;
                default:
                    // TODO:
                    break;
            }
        }
        talkListHead = currentGameInfo.getTalkList().size();
    }

    @Override
    public void initialize(GameInfo gameInfo, GameSetting gameSetting) {
        me = gameInfo.getAgent();
        grayList = new ArrayList<>(gameInfo.getAgentList());
        grayList.remove(me);
        villagerList.clear();
        werewolfList.clear();
        myDivinationQueue.clear();
    }

    @Override
    public void dayStart() {
        Judge divination = currentGameInfo.getDivineResult();
        talkListHead = 0;
        if (divination == null) {
            return;
        }

        myDivinationQueue.offer(divination);
        Agent target = divination.getTarget();
        grayList.remove(target);
        Species result = divination.getResult();
        if (result == Species.HUMAN) {
            villagerList.add(target);
        } else {
            werewolfList.add(target);
        }
    }

    @Override
    public String talk() {
        if (!saidCo) {
            if (!myDivinationQueue.isEmpty() && myDivinationQueue.peekLast().getResult() == Species.WEREWOLF) {
                saidCo = true;
                ContentBuilder builder = new ComingoutContentBuilder(me, Role.SEER);
                return new Content(builder).getText();
            }
        } else {
            if (!myDivinationQueue.isEmpty()) {
                Judge divination = myDivinationQueue.poll();
                ContentBuilder builder = new DivinedResultContentBuilder(divination.getTarget(), divination.getResult());
                return new Content(builder).getText();
            }
        }

        return Content.OVER.getText();
    }

    @Override
    public String whisper() {
        return null;
    }

    @Override
    public Agent vote() {
        List<Agent> candidates = new ArrayList<>();
        werewolfList.forEach((w) -> {
            if (isAlive(w)) {
                candidates.add(w);
            }
        });
        if (candidates.isEmpty()) {
            grayList.forEach((g) -> {
                if (isAlive(g)) {
                    candidates.add(g);
                }
            });
        }
        if (candidates.isEmpty()) {
            return null;
        }
        return randomSelect(candidates);
    }

    @Override
    public Agent attack() {
        return null;
    }

    @Override
    public Agent divine() {
        List<Agent> candidates = new ArrayList<>();
        grayList.forEach((g) -> {
            if (isAlive(g)) {
                candidates.add(g);
            }
        });
        if (candidates.isEmpty()) {
            return null;
        }
        return randomSelect(candidates);
    }

    @Override
    public Agent guard() {
        return null;
    }

    @Override
    public void finish() {

    }

    private boolean isAlive(Agent agent) {
        return currentGameInfo.getAliveAgentList().contains(agent);
    }

    private <T> T randomSelect(List<T> list) {
        return list.isEmpty() ? null : list.get((int) (Math.random() * list.size()));
    }
}

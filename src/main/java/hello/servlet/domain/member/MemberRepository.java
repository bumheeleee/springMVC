package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */

public class MemberRepository {
    //key = id, value = member
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    //싱글톤으로 만듬
    //저장하거나 검색하거나 하는 함수를 호출할 때마다 객체를 생성하면 너무 비효율적임
    //spring을 사용하면 알아서 싱글톤으로 만들어 주기는 한다.
    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance(){
        return instance;
    }

    //생성자로 객체를 생성하는것을 막아줌
    private MemberRepository(){
    }

    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    //ArrayList 사용법
    //ArrayList<Integer> integers = new ArrayList<>(); 이런식으로 사용
    public ArrayList<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }

}

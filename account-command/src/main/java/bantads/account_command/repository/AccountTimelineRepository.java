package bantads.account_command.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bantads.account_command.entity.timeline.AccountTimeline;

public interface AccountTimelineRepository extends JpaRepository<AccountTimeline, Long>{
}

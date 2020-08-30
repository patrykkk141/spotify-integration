package pl.patryk.spotifyintegration.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseSearchResult<R> {

  private Integer limit;
  private String next;
  private Integer offset;
  private String previous;
  private Integer total;
  private List<R> items;

}

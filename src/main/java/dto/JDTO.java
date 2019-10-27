package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JDTO {
	String[] vers;
	String name;
	@Builder.Default
	String[] tags = new String[] {};
	Long did;
	Long usn;
	List<Field> flds;
	Integer sortf;
	String latexPre;
	List<TemplateDTO> tmpls;
	String latexPost;
	Integer type;
	Long id;
	String css;
	Long mod;
	//	@JsonIgnore
	List<Object> req;

	// @formatter:off
	final static String LATEX_PRE =
			"\\documentclass[12pt]{article}\n\\special{papersize=3in,5in}\n\\usepackage{amssymb,amsmath}\n\\pagestyle{empty}\n\\setlength{\\parindent}{0in}\n\\begin{document}\n";
	// @formatter:on

	public static JDTO create() {
		return JDTO.builder()
				.vers(new String[] {})
				.name("Basic")
				.tags(new String[] {})
				.usn(-1L)
				.req(asList(asList(0, "all", new int[] { 0 })))
				.sortf(0)
				.latexPre(LATEX_PRE)
				.latexPost("\\end{document}")
				.type(0)
				.build();
	}

	public JDTO setVers(String[] vers) {
		this.vers = vers;
		return this;
	}

	public JDTO setName(String name) {
		this.name = name;
		return this;
	}

	public JDTO setTags(String[] tags) {
		this.tags = tags;
		return this;
	}

	public JDTO setDid(Long did) {
		this.did = did;
		return this;
	}

	public JDTO setUsn(Long usn) {
		this.usn = usn;
		return this;
	}

	public JDTO setFlds(List<Field> flds) {
		this.flds = flds;
		return this;
	}

	public JDTO setSortf(Integer sortf) {
		this.sortf = sortf;
		return this;
	}

	public JDTO setLatexPre(String latexPre) {
		this.latexPre = latexPre;
		return this;
	}

	public JDTO setTmpls(List<TemplateDTO> tmpls) {
		this.tmpls = tmpls;
		return this;
	}

	public JDTO setLatexPost(String latexPost) {
		this.latexPost = latexPost;
		return this;
	}

	public JDTO setType(Integer type) {
		this.type = type;
		return this;
	}

	public JDTO setId(Long id) {
		this.id = id;
		return this;
	}

	public JDTO setCss(String css) {
		this.css = css;
		return this;
	}

	public JDTO setMod(Long mod) {
		this.mod = mod;
		return this;
	}

	public JDTO setReq(ArrayList<Object> req) {
		this.req = req;
		return this;
	}
}

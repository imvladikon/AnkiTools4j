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
public class ColModelsJObject {
	//CSS, shared for all templates
	String css;
	//Long specifying the id of the deck that cards are added to by default
	Long did;
	//JSONArray containing object for each field in the model as follows
	List<Field> flds;
	//model ID, matches notes.mid
	Long id;
	//String added to end of LaTeX expressions (usually \\end{document})
	String latexPost;
	//preamble for LaTeX expressions
	String latexPre;
	//modification time in seconds
	Long mod;
	//model name
	String name;
	// read https://github.com/ankidroid/Anki-Android/wiki/Database-Structure
	List<Object> req;
	//Integer specifying which field is used for sorting in the browser
	Integer sortf;
	//Anki saves the tags of the last added note to the current model, use an empty array []
	@Builder.Default
	String[] tags = new String[] {};
	//JSONArray containing object of CardTemplate for each card in model
	List<TemplateJObject> tmpls;
	//Integer specifying what type of model. 0 for standard, 1 for cloze
	Integer type;
	//usn: Update sequence number: used in same way as other usn vales in db
	Long usn;
	//Legacy version number (unused), use an empty array []
	String[] vers;

	// @formatter:off
	final static String LATEX_PRE =
			"\\documentclass[12pt]{article}\n\\special{papersize=3in,5in}\n\\usepackage{amssymb,amsmath}\n\\pagestyle{empty}\n\\setlength{\\parindent}{0in}\n\\begin{document}\n";
	final static String LATEX_POST = "\\end{document}";
	// @formatter:on

	public static ColModelsJObject create() {
		return ColModelsJObject.builder()
				.vers(new String[] {})
				.name("Basic")
				.tags(new String[] {})
				.usn(-1L)
				.req(asList(asList(0, "all", new int[] { 0 })))
				.sortf(0)
				.latexPre(LATEX_PRE)
				.latexPost(LATEX_POST)
				.type(0)
				.build();
	}

	public ColModelsJObject setVers(String[] vers) {
		this.vers = vers;
		return this;
	}

	public ColModelsJObject setName(String name) {
		this.name = name;
		return this;
	}

	public ColModelsJObject setTags(String[] tags) {
		this.tags = tags;
		return this;
	}

	public ColModelsJObject setDid(Long did) {
		this.did = did;
		return this;
	}

	public ColModelsJObject setUsn(Long usn) {
		this.usn = usn;
		return this;
	}

	public ColModelsJObject setFlds(List<Field> flds) {
		this.flds = flds;
		return this;
	}

	public ColModelsJObject setSortf(Integer sortf) {
		this.sortf = sortf;
		return this;
	}

	public ColModelsJObject setLatexPre(String latexPre) {
		this.latexPre = latexPre;
		return this;
	}

	public ColModelsJObject setTmpls(List<TemplateJObject> tmpls) {
		this.tmpls = tmpls;
		return this;
	}

	public ColModelsJObject setLatexPost(String latexPost) {
		this.latexPost = latexPost;
		return this;
	}

	public ColModelsJObject setType(Integer type) {
		this.type = type;
		return this;
	}

	public ColModelsJObject setId(Long id) {
		this.id = id;
		return this;
	}

	public ColModelsJObject setCss(String css) {
		this.css = css;
		return this;
	}

	public ColModelsJObject setMod(Long mod) {
		this.mod = mod;
		return this;
	}

	public ColModelsJObject setReq(ArrayList<Object> req) {
		this.req = req;
		return this;
	}
}

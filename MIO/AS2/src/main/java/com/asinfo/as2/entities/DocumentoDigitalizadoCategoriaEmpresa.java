/*  1:   */ package com.asinfo.as2.entities;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import javax.persistence.Column;
/*  5:   */ import javax.persistence.Entity;
/*  6:   */ import javax.persistence.FetchType;
/*  7:   */ import javax.persistence.GeneratedValue;
/*  8:   */ import javax.persistence.GenerationType;
/*  9:   */ import javax.persistence.Id;
/* 10:   */ import javax.persistence.JoinColumn;
/* 11:   */ import javax.persistence.ManyToOne;
/* 12:   */ import javax.persistence.Table;
/* 13:   */ import javax.persistence.TableGenerator;
/* 14:   */ import javax.validation.constraints.NotNull;
/* 15:   */ 
/* 16:   */ @Entity
/* 17:   */ @Table(name="documento_digitalizado_categoria_empresa")
/* 18:   */ public class DocumentoDigitalizadoCategoriaEmpresa
/* 19:   */   extends EntidadBase
/* 20:   */   implements Serializable
/* 21:   */ {
/* 22:   */   private static final long serialVersionUID = 3233812315999325055L;
/* 23:   */   @Id
/* 24:   */   @TableGenerator(name="documento_digitalizado_categoria_empresa", initialValue=0, allocationSize=50)
/* 25:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="documento_digitalizado_categoria_empresa")
/* 26:   */   @Column(name="id_documento_digitalizado_categoria_empresa", unique=true, nullable=false)
/* 27:   */   private int idDocumentoDigitalizadoCategoriaEmpresa;
/* 28:   */   @Column(name="requerido", nullable=false)
/* 29:   */   private boolean requerido;
/* 30:   */   @ManyToOne(fetch=FetchType.LAZY)
/* 31:   */   @JoinColumn(name="id_documento_digitalizado", nullable=false, insertable=true, updatable=false)
/* 32:   */   @NotNull
/* 33:   */   private DocumentoDigitalizado documentoDigitalizado;
/* 34:   */   @ManyToOne(fetch=FetchType.LAZY)
/* 35:   */   @JoinColumn(name="id_categoriaEmpresa", nullable=false, insertable=true, updatable=false)
/* 36:   */   @NotNull
/* 37:   */   private CategoriaEmpresa categoriaEmpresa;
/* 38:   */   
/* 39:   */   public boolean isRequerido()
/* 40:   */   {
/* 41:55 */     return this.requerido;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public int getIdDocumentoDigitalizadoCategoriaEmpresa()
/* 45:   */   {
/* 46:59 */     return this.idDocumentoDigitalizadoCategoriaEmpresa;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void setIdDocumentoDigitalizadoCategoriaEmpresa(int idDocumentoDigitalizadoCategoriaEmpresa)
/* 50:   */   {
/* 51:63 */     this.idDocumentoDigitalizadoCategoriaEmpresa = idDocumentoDigitalizadoCategoriaEmpresa;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public DocumentoDigitalizado getDocumentoDigitalizado()
/* 55:   */   {
/* 56:67 */     return this.documentoDigitalizado;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void setDocumentoDigitalizado(DocumentoDigitalizado documentoDigitalizado)
/* 60:   */   {
/* 61:71 */     this.documentoDigitalizado = documentoDigitalizado;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public CategoriaEmpresa getCategoriaEmpresa()
/* 65:   */   {
/* 66:75 */     return this.categoriaEmpresa;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa)
/* 70:   */   {
/* 71:79 */     this.categoriaEmpresa = categoriaEmpresa;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public void setRequerido(boolean requerido)
/* 75:   */   {
/* 76:83 */     this.requerido = requerido;
/* 77:   */   }
/* 78:   */   
/* 79:   */   public int getId()
/* 80:   */   {
/* 81:92 */     return this.idDocumentoDigitalizadoCategoriaEmpresa;
/* 82:   */   }
/* 83:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DocumentoDigitalizadoCategoriaEmpresa
 * JD-Core Version:    0.7.0.1
 */
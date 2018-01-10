/*  1:   */ package com.asinfo.as2.entities;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.List;
/*  6:   */ import javax.persistence.Column;
/*  7:   */ import javax.persistence.Entity;
/*  8:   */ import javax.persistence.FetchType;
/*  9:   */ import javax.persistence.GeneratedValue;
/* 10:   */ import javax.persistence.GenerationType;
/* 11:   */ import javax.persistence.Id;
/* 12:   */ import javax.persistence.JoinColumn;
/* 13:   */ import javax.persistence.ManyToOne;
/* 14:   */ import javax.persistence.OneToMany;
/* 15:   */ import javax.persistence.Table;
/* 16:   */ import javax.persistence.TableGenerator;
/* 17:   */ import javax.validation.constraints.NotNull;
/* 18:   */ 
/* 19:   */ @Entity
/* 20:   */ @Table(name="documento_digitalizado_departamento")
/* 21:   */ public class DocumentoDigitalizadoDepartamento
/* 22:   */   extends EntidadBase
/* 23:   */   implements Serializable
/* 24:   */ {
/* 25:   */   private static final long serialVersionUID = 3233812315999325055L;
/* 26:   */   @Id
/* 27:   */   @TableGenerator(name="documento_digitalizado_departamento", initialValue=0, allocationSize=50)
/* 28:   */   @GeneratedValue(strategy=GenerationType.TABLE, generator="documento_digitalizado_departamento")
/* 29:   */   @Column(name="id_documento_digitalizado_departamento", unique=true, nullable=false)
/* 30:   */   private int idDocumentoDigitalizadoDepartamento;
/* 31:   */   @Column(name="requerido", nullable=false)
/* 32:   */   private boolean requerido;
/* 33:   */   @ManyToOne(fetch=FetchType.LAZY)
/* 34:   */   @JoinColumn(name="id_documento_digitalizado", nullable=false, insertable=true, updatable=false)
/* 35:   */   @NotNull
/* 36:   */   private DocumentoDigitalizado documentoDigitalizado;
/* 37:   */   @ManyToOne(fetch=FetchType.LAZY)
/* 38:   */   @JoinColumn(name="id_departamento", nullable=false, insertable=true, updatable=false)
/* 39:   */   @NotNull
/* 40:   */   private Departamento departamento;
/* 41:   */   @OneToMany(fetch=FetchType.LAZY, mappedBy="documentoDigitalizadoDepartamento")
/* 42:57 */   private List<DetalleDocumentoDigitalizado> listaDetalleDocumentoDigitalizadoEmpleado = new ArrayList();
/* 43:   */   
/* 44:   */   public boolean isRequerido()
/* 45:   */   {
/* 46:62 */     return this.requerido;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public int getIdDocumentoDigitalizadoDepartamento()
/* 50:   */   {
/* 51:66 */     return this.idDocumentoDigitalizadoDepartamento;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void setIdDocumentoDigitalizadoDepartamento(int idDocumentoDigitalizadoDepartamento)
/* 55:   */   {
/* 56:71 */     this.idDocumentoDigitalizadoDepartamento = idDocumentoDigitalizadoDepartamento;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public DocumentoDigitalizado getDocumentoDigitalizado()
/* 60:   */   {
/* 61:75 */     return this.documentoDigitalizado;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public void setDocumentoDigitalizado(DocumentoDigitalizado documentoDigitalizado)
/* 65:   */   {
/* 66:79 */     this.documentoDigitalizado = documentoDigitalizado;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public Departamento getDepartamento()
/* 70:   */   {
/* 71:83 */     return this.departamento;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public void setDepartamento(Departamento departamento)
/* 75:   */   {
/* 76:87 */     this.departamento = departamento;
/* 77:   */   }
/* 78:   */   
/* 79:   */   public void setRequerido(boolean requerido)
/* 80:   */   {
/* 81:91 */     this.requerido = requerido;
/* 82:   */   }
/* 83:   */   
/* 84:   */   public int getId()
/* 85:   */   {
/* 86:98 */     return this.idDocumentoDigitalizadoDepartamento;
/* 87:   */   }
/* 88:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DocumentoDigitalizadoDepartamento
 * JD-Core Version:    0.7.0.1
 */
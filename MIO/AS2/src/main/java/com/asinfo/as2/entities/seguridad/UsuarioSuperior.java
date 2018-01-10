/*   1:    */ package com.asinfo.as2.entities.seguridad;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.EnumType;
/*   8:    */ import javax.persistence.Enumerated;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="usuario_superior", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"documento_base", "id_superior", "id_entidad_usuario"})})
/*  21:    */ public class UsuarioSuperior
/*  22:    */   extends EntidadBase
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="usuario_superior", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="usuario_superior")
/*  28:    */   @Column(name="id_usuario_superior")
/*  29:    */   private int idUsuarioSuperior;
/*  30:    */   @Enumerated(EnumType.ORDINAL)
/*  31:    */   @Column(name="documento_base", nullable=false)
/*  32:    */   @NotNull
/*  33:    */   private DocumentoBase documentoBase;
/*  34:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  35:    */   @JoinColumn(name="id_superior", nullable=false)
/*  36:    */   @NotNull
/*  37:    */   private EntidadUsuario superior;
/*  38:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  39:    */   @JoinColumn(name="id_entidad_usuario", nullable=false)
/*  40:    */   @NotNull
/*  41:    */   private EntidadUsuario entidadUsuario;
/*  42:    */   
/*  43:    */   public int getId()
/*  44:    */   {
/*  45: 73 */     return this.idUsuarioSuperior;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int getIdUsuarioSuperior()
/*  49:    */   {
/*  50: 80 */     return this.idUsuarioSuperior;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setIdUsuarioSuperior(int idUsuarioSuperior)
/*  54:    */   {
/*  55: 84 */     this.idUsuarioSuperior = idUsuarioSuperior;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public DocumentoBase getDocumentoBase()
/*  59:    */   {
/*  60: 88 */     return this.documentoBase;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/*  64:    */   {
/*  65: 92 */     this.documentoBase = documentoBase;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public EntidadUsuario getSuperior()
/*  69:    */   {
/*  70: 96 */     return this.superior;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setSuperior(EntidadUsuario superior)
/*  74:    */   {
/*  75:100 */     this.superior = superior;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public EntidadUsuario getEntidadUsuario()
/*  79:    */   {
/*  80:104 */     return this.entidadUsuario;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setEntidadUsuario(EntidadUsuario entidadUsuario)
/*  84:    */   {
/*  85:108 */     this.entidadUsuario = entidadUsuario;
/*  86:    */   }
/*  87:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.seguridad.UsuarioSuperior
 * JD-Core Version:    0.7.0.1
 */
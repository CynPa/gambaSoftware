/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.seguridad.EntidadSistema;
/*   4:    */ import java.util.Date;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.OneToOne;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.persistence.Temporal;
/*  16:    */ import javax.persistence.TemporalType;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="version_sistema")
/*  22:    */ public class VersionSistema
/*  23:    */   extends EntidadBase
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="version_sistema", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="version_sistema")
/*  29:    */   @Column(name="id_version_sistema")
/*  30:    */   private int idVersionSistema;
/*  31:    */   @Column(name="numero", nullable=false, length=10)
/*  32:    */   @NotNull
/*  33:    */   @Size(max=10)
/*  34:    */   private String numero;
/*  35:    */   @Column(name="fecha", nullable=false)
/*  36:    */   @Temporal(TemporalType.DATE)
/*  37:    */   @NotNull
/*  38:    */   private Date fecha;
/*  39:    */   @OneToOne(fetch=FetchType.LAZY)
/*  40:    */   @JoinColumn(name="id_sistema")
/*  41:    */   private EntidadSistema sistema;
/*  42:    */   
/*  43:    */   public int getId()
/*  44:    */   {
/*  45: 81 */     return this.idVersionSistema;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int getIdVersionSistema()
/*  49:    */   {
/*  50: 88 */     return this.idVersionSistema;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setIdVersionSistema(int idVersionSistema)
/*  54:    */   {
/*  55: 96 */     this.idVersionSistema = idVersionSistema;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getNumero()
/*  59:    */   {
/*  60:103 */     return this.numero;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setNumero(String numero)
/*  64:    */   {
/*  65:111 */     this.numero = numero;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Date getFecha()
/*  69:    */   {
/*  70:118 */     return this.fecha;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setFecha(Date fecha)
/*  74:    */   {
/*  75:126 */     this.fecha = fecha;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public EntidadSistema getSistema()
/*  79:    */   {
/*  80:133 */     return this.sistema;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setSistema(EntidadSistema sistema)
/*  84:    */   {
/*  85:141 */     this.sistema = sistema;
/*  86:    */   }
/*  87:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.VersionSistema
 * JD-Core Version:    0.7.0.1
 */
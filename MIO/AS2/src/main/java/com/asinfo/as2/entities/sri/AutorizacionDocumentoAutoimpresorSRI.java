/*   1:    */ package com.asinfo.as2.entities.sri;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.EnumType;
/*   8:    */ import javax.persistence.Enumerated;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.validation.constraints.NotNull;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="autorizacion_documento_autoimpresor_SRI")
/*  20:    */ public class AutorizacionDocumentoAutoimpresorSRI
/*  21:    */   extends EntidadBase
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="autorizacion_documento_autoimpresor_SRI", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="autorizacion_documento_autoimpresor_SRI")
/*  27:    */   @Column(name="id_autorizacion_documento_autoimpresor_SRI")
/*  28:    */   private int idAutorizacionDocumentoAutoimpresorSRI;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   private int idSucursal;
/*  33:    */   @ManyToOne
/*  34:    */   @JoinColumn(name="id_autorizacion_autoimpresor_SRI", nullable=true)
/*  35:    */   private AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI;
/*  36:    */   @Enumerated(EnumType.ORDINAL)
/*  37:    */   @Column(name="documento_base", nullable=false)
/*  38:    */   @NotNull
/*  39:    */   private DocumentoBase documentoBase;
/*  40:    */   @Column(name="activo", nullable=false)
/*  41:    */   private boolean activo;
/*  42:    */   
/*  43:    */   public int getId()
/*  44:    */   {
/*  45: 68 */     return this.idAutorizacionDocumentoAutoimpresorSRI;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int getIdAutorizacionDocumentoAutoimpresorSRI()
/*  49:    */   {
/*  50: 72 */     return this.idAutorizacionDocumentoAutoimpresorSRI;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setIdAutorizacionDocumentoAutoimpresorSRI(int idAutorizacionDocumentoAutoimpresorSRI)
/*  54:    */   {
/*  55: 76 */     this.idAutorizacionDocumentoAutoimpresorSRI = idAutorizacionDocumentoAutoimpresorSRI;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getIdOrganizacion()
/*  59:    */   {
/*  60: 80 */     return this.idOrganizacion;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setIdOrganizacion(int idOrganizacion)
/*  64:    */   {
/*  65: 84 */     this.idOrganizacion = idOrganizacion;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdSucursal()
/*  69:    */   {
/*  70: 88 */     return this.idSucursal;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdSucursal(int idSucursal)
/*  74:    */   {
/*  75: 92 */     this.idSucursal = idSucursal;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public DocumentoBase getDocumentoBase()
/*  79:    */   {
/*  80: 96 */     return this.documentoBase;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/*  84:    */   {
/*  85:100 */     this.documentoBase = documentoBase;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean isActivo()
/*  89:    */   {
/*  90:104 */     return this.activo;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setActivo(boolean activo)
/*  94:    */   {
/*  95:108 */     this.activo = activo;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public AutorizacionAutoimpresorSRI getAutorizacionAutoimpresorSRI()
/*  99:    */   {
/* 100:112 */     return this.autorizacionAutoimpresorSRI;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setAutorizacionAutoimpresorSRI(AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI)
/* 104:    */   {
/* 105:116 */     this.autorizacionAutoimpresorSRI = autorizacionAutoimpresorSRI;
/* 106:    */   }
/* 107:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.sri.AutorizacionDocumentoAutoimpresorSRI
 * JD-Core Version:    0.7.0.1
 */
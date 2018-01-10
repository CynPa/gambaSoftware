/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.JoinColumn;
/*   9:    */ import javax.persistence.ManyToOne;
/*  10:    */ import javax.persistence.Table;
/*  11:    */ import javax.persistence.TableGenerator;
/*  12:    */ 
/*  13:    */ @Entity
/*  14:    */ @Table(name="documento_gasto_importacion")
/*  15:    */ public class DocumentoGastoImportacion
/*  16:    */   extends EntidadBase
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = 6076440916856538484L;
/*  19:    */   @Id
/*  20:    */   @TableGenerator(name="documento_gasto_importacion", initialValue=0, allocationSize=50)
/*  21:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="documento_gasto_importacion")
/*  22:    */   @Column(name="id_documento_gasto_importacion")
/*  23:    */   private int idDocumentoGastoImportacion;
/*  24:    */   @Column(name="id_organizacion", nullable=false)
/*  25:    */   private int idOrganizacion;
/*  26:    */   @Column(name="id_sucursal", nullable=false)
/*  27:    */   private int idSucursal;
/*  28:    */   @Column(name="indicador_gasto_obligatorio", nullable=false)
/*  29:    */   private boolean indicadorGastoObligatorio;
/*  30:    */   @ManyToOne
/*  31:    */   @JoinColumn(name="id_documento", nullable=true)
/*  32:    */   private Documento documento;
/*  33:    */   @ManyToOne
/*  34:    */   @JoinColumn(name="id_gasto_importacion", nullable=true)
/*  35:    */   private GastoImportacion gastoImportacion;
/*  36:    */   
/*  37:    */   public int getId()
/*  38:    */   {
/*  39: 85 */     return getIdDocumentoGastoImportacion();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int getIdOrganizacion()
/*  43:    */   {
/*  44: 98 */     return this.idOrganizacion;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setIdOrganizacion(int idOrganizacion)
/*  48:    */   {
/*  49:106 */     this.idOrganizacion = idOrganizacion;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int getIdSucursal()
/*  53:    */   {
/*  54:114 */     return this.idSucursal;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setIdSucursal(int idSucursal)
/*  58:    */   {
/*  59:122 */     this.idSucursal = idSucursal;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Documento getDocumento()
/*  63:    */   {
/*  64:130 */     return this.documento;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setDocumento(Documento documento)
/*  68:    */   {
/*  69:138 */     this.documento = documento;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public GastoImportacion getGastoImportacion()
/*  73:    */   {
/*  74:146 */     return this.gastoImportacion;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setGastoImportacion(GastoImportacion gastoImportacion)
/*  78:    */   {
/*  79:154 */     this.gastoImportacion = gastoImportacion;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int getIdDocumentoGastoImportacion()
/*  83:    */   {
/*  84:163 */     return this.idDocumentoGastoImportacion;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setIdDocumentoGastoImportacion(int idDocumentoGastoImportacion)
/*  88:    */   {
/*  89:172 */     this.idDocumentoGastoImportacion = idDocumentoGastoImportacion;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public boolean isIndicadorGastoObligatorio()
/*  93:    */   {
/*  94:181 */     return this.indicadorGastoObligatorio;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setIndicadorGastoObligatorio(boolean indicadorGastoObligatorio)
/*  98:    */   {
/*  99:190 */     this.indicadorGastoObligatorio = indicadorGastoObligatorio;
/* 100:    */   }
/* 101:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DocumentoGastoImportacion
 * JD-Core Version:    0.7.0.1
 */
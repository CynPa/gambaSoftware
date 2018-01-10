/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
/*   4:    */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="comprobanteSRI_credito_tributarioSRI", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_tipo_identificacion", "id_tipo_comprobanteSRI", "id_credito_tributarioSRI"})})
/*  18:    */ public class ComprobanteSRICreditoTributarioSRI
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="comprobanteSRI_credito_tributarioSRI", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="comprobanteSRI_credito_tributarioSRI")
/*  25:    */   @Column(name="id_comprobanteSRI_credito_tributarioSRI")
/*  26:    */   private int idComprobanteSRICreditoTributarioSRI;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal", nullable=false)
/*  30:    */   private int idSucursal;
/*  31:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  32:    */   @JoinColumn(name="id_tipo_identificacion", nullable=false)
/*  33:    */   private TipoIdentificacion tipoIdentificacion;
/*  34:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  35:    */   @JoinColumn(name="id_tipo_comprobanteSRI", nullable=false)
/*  36:    */   private TipoComprobanteSRI tipoComprobanteSRI;
/*  37:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  38:    */   @JoinColumn(name="id_credito_tributarioSRI", nullable=false)
/*  39:    */   private CreditoTributarioSRI creditoTributarioSRI;
/*  40:    */   
/*  41:    */   public int getId()
/*  42:    */   {
/*  43: 85 */     return this.idComprobanteSRICreditoTributarioSRI;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getIdComprobanteSRICreditoTributarioSRI()
/*  47:    */   {
/*  48: 92 */     return this.idComprobanteSRICreditoTributarioSRI;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public TipoIdentificacion getTipoIdentificacion()
/*  52:    */   {
/*  53: 99 */     return this.tipoIdentificacion;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion)
/*  57:    */   {
/*  58:107 */     this.tipoIdentificacion = tipoIdentificacion;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdComprobanteSRICreditoTributarioSRI(int idComprobanteSRICreditoTributarioSRI)
/*  62:    */   {
/*  63:115 */     this.idComprobanteSRICreditoTributarioSRI = idComprobanteSRICreditoTributarioSRI;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdOrganizacion()
/*  67:    */   {
/*  68:122 */     return this.idOrganizacion;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdOrganizacion(int idOrganizacion)
/*  72:    */   {
/*  73:130 */     this.idOrganizacion = idOrganizacion;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getIdSucursal()
/*  77:    */   {
/*  78:137 */     return this.idSucursal;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIdSucursal(int idSucursal)
/*  82:    */   {
/*  83:145 */     this.idSucursal = idSucursal;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public TipoComprobanteSRI getTipoComprobanteSRI()
/*  87:    */   {
/*  88:152 */     return this.tipoComprobanteSRI;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setTipoComprobanteSRI(TipoComprobanteSRI tipoComprobanteSRI)
/*  92:    */   {
/*  93:160 */     this.tipoComprobanteSRI = tipoComprobanteSRI;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public CreditoTributarioSRI getCreditoTributarioSRI()
/*  97:    */   {
/*  98:167 */     return this.creditoTributarioSRI;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setCreditoTributarioSRI(CreditoTributarioSRI creditoTributarioSRI)
/* 102:    */   {
/* 103:175 */     this.creditoTributarioSRI = creditoTributarioSRI;
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ComprobanteSRICreditoTributarioSRI
 * JD-Core Version:    0.7.0.1
 */
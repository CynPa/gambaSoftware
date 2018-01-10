/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.FetchType;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.JoinColumn;
/*  10:    */ import javax.persistence.ManyToOne;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.validation.constraints.Size;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="banco_acreedor")
/*  17:    */ public class BancoAcreedor
/*  18:    */   extends EntidadBase
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 1L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="banco_acreedor", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="banco_acreedor")
/*  24:    */   @Column(name="id_banco_acreedor", unique=true, nullable=false)
/*  25:    */   private int idBancoAcreedor;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   @Column(name="id_sucursal", nullable=false)
/*  29:    */   private int idSucursal;
/*  30:    */   @Column(name="descripcion", nullable=true, length=200)
/*  31:    */   @Size(max=200)
/*  32:    */   private String descripcion;
/*  33:    */   @Column(name="activo", nullable=false)
/*  34:    */   private boolean activo;
/*  35:    */   @Column(name="predeterminado", nullable=false)
/*  36:    */   private boolean predeterminado;
/*  37:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  38:    */   @JoinColumn(name="id_banco", nullable=true)
/*  39:    */   private Banco banco;
/*  40:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  41:    */   @JoinColumn(name="id_cuenta_bancaria_organizacion", nullable=true)
/*  42:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  43:    */   
/*  44:    */   public int getId()
/*  45:    */   {
/*  46: 61 */     return this.idBancoAcreedor;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int getIdOrganizacion()
/*  50:    */   {
/*  51: 65 */     return this.idOrganizacion;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setIdOrganizacion(int idOrganizacion)
/*  55:    */   {
/*  56: 69 */     this.idOrganizacion = idOrganizacion;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getIdSucursal()
/*  60:    */   {
/*  61: 73 */     return this.idSucursal;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setIdSucursal(int idSucursal)
/*  65:    */   {
/*  66: 77 */     this.idSucursal = idSucursal;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getDescripcion()
/*  70:    */   {
/*  71: 81 */     return this.descripcion;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setDescripcion(String descripcion)
/*  75:    */   {
/*  76: 85 */     this.descripcion = descripcion;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean isActivo()
/*  80:    */   {
/*  81: 89 */     return this.activo;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setActivo(boolean activo)
/*  85:    */   {
/*  86: 93 */     this.activo = activo;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean isPredeterminado()
/*  90:    */   {
/*  91: 97 */     return this.predeterminado;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setPredeterminado(boolean predeterminado)
/*  95:    */   {
/*  96:101 */     this.predeterminado = predeterminado;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int getIdBancoAcreedor()
/* 100:    */   {
/* 101:105 */     return this.idBancoAcreedor;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setIdBancoAcreedor(int idBancoAcreedor)
/* 105:    */   {
/* 106:109 */     this.idBancoAcreedor = idBancoAcreedor;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Banco getBanco()
/* 110:    */   {
/* 111:113 */     return this.banco;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setBanco(Banco banco)
/* 115:    */   {
/* 116:117 */     this.banco = banco;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 120:    */   {
/* 121:121 */     return this.cuentaBancariaOrganizacion;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 125:    */   {
/* 126:125 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 127:    */   }
/* 128:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.BancoAcreedor
 * JD-Core Version:    0.7.0.1
 */
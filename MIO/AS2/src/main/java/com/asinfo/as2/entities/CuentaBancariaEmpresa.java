/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.ManyToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.Size;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="cuenta_bancaria_empresa")
/*  18:    */ public class CuentaBancariaEmpresa
/*  19:    */   extends EntidadBase
/*  20:    */   implements Serializable
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 1L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="cuenta_bancaria_empresa", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="cuenta_bancaria_empresa")
/*  26:    */   @Column(name="id_cuenta_bancaria_empresa", unique=true, nullable=false)
/*  27:    */   private int idCuentaBancariaEmpresa;
/*  28:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  29:    */   @JoinColumn(name="id_empresa", nullable=true)
/*  30:    */   private Empresa empresa;
/*  31:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  32:    */   @JoinColumn(name="id_cuenta_bancaria", nullable=true)
/*  33:    */   private CuentaBancaria cuentaBancaria;
/*  34:    */   @Column(name="id_organizacion")
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="id_sucursal")
/*  37:    */   private Integer idSucursal;
/*  38:    */   @Column(name="activo", nullable=false)
/*  39:    */   private boolean activo;
/*  40:    */   @Column(name="descripcion", length=200)
/*  41:    */   @Size(min=2, max=200)
/*  42:    */   private String descripcion;
/*  43:    */   @Column(name="predeterminado", nullable=false)
/*  44:    */   private boolean predeterminado;
/*  45:    */   
/*  46:    */   public CuentaBancariaEmpresa()
/*  47:    */   {
/*  48: 57 */     this.activo = true;
/*  49: 58 */     setDescripcion("N/A");
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int getIdCuentaBancariaEmpresa()
/*  53:    */   {
/*  54: 67 */     return this.idCuentaBancariaEmpresa;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setIdCuentaBancariaEmpresa(int idCuentaBancariaEmpresa)
/*  58:    */   {
/*  59: 77 */     this.idCuentaBancariaEmpresa = idCuentaBancariaEmpresa;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Empresa getEmpresa()
/*  63:    */   {
/*  64: 86 */     return this.empresa;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setEmpresa(Empresa empresa)
/*  68:    */   {
/*  69: 96 */     this.empresa = empresa;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public CuentaBancaria getCuentaBancaria()
/*  73:    */   {
/*  74:105 */     return this.cuentaBancaria;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setCuentaBancaria(CuentaBancaria cuentaBancaria)
/*  78:    */   {
/*  79:115 */     this.cuentaBancaria = cuentaBancaria;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int getIdOrganizacion()
/*  83:    */   {
/*  84:124 */     return this.idOrganizacion;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setIdOrganizacion(int idOrganizacion)
/*  88:    */   {
/*  89:134 */     this.idOrganizacion = idOrganizacion;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Integer getIdSucursal()
/*  93:    */   {
/*  94:143 */     return this.idSucursal;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setIdSucursal(Integer idSucursal)
/*  98:    */   {
/*  99:153 */     this.idSucursal = idSucursal;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String getDescripcion()
/* 103:    */   {
/* 104:162 */     return this.descripcion;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setDescripcion(String descripcion)
/* 108:    */   {
/* 109:172 */     this.descripcion = descripcion;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean isActivo()
/* 113:    */   {
/* 114:181 */     return this.activo;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setActivo(boolean activo)
/* 118:    */   {
/* 119:191 */     this.activo = activo;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean isPredeterminado()
/* 123:    */   {
/* 124:200 */     return this.predeterminado;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setPredeterminado(boolean predeterminado)
/* 128:    */   {
/* 129:210 */     this.predeterminado = predeterminado;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String toString()
/* 133:    */   {
/* 134:215 */     return this.cuentaBancaria.getNumero();
/* 135:    */   }
/* 136:    */   
/* 137:    */   public int getId()
/* 138:    */   {
/* 139:220 */     return this.idCuentaBancariaEmpresa;
/* 140:    */   }
/* 141:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CuentaBancariaEmpresa
 * JD-Core Version:    0.7.0.1
 */
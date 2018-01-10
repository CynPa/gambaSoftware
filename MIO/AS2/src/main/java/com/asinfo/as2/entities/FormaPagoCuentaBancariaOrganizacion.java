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
/*  13:    */ 
/*  14:    */ @Entity
/*  15:    */ @Table(name="forma_pago_cuenta_bancaria_organizacion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_cuenta_bancaria_organizacion", "id_forma_pago"})})
/*  16:    */ public class FormaPagoCuentaBancariaOrganizacion
/*  17:    */   extends EntidadBase
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = 1L;
/*  20:    */   @Id
/*  21:    */   @TableGenerator(name="forma_pago_cuenta_bancaria_organizacion", initialValue=0, allocationSize=50)
/*  22:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="forma_pago_cuenta_bancaria_organizacion")
/*  23:    */   @Column(name="id_forma_pago_cuenta_bancaria_organizacion")
/*  24:    */   private int idFormaPagoCuentaBancariaOrganizacion;
/*  25:    */   @Column(name="id_organizacion", nullable=false)
/*  26:    */   private int idOrganizacion;
/*  27:    */   @Column(name="id_sucursal", nullable=false)
/*  28:    */   private int idSucursal;
/*  29:    */   @Column(name="indicador_debito_bancario", nullable=false)
/*  30:    */   private boolean indicadorDebitoBancario;
/*  31:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  32:    */   @JoinColumn(name="id_cuenta_bancaria_organizacion")
/*  33:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  34:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  35:    */   @JoinColumn(name="id_forma_pago")
/*  36:    */   private FormaPago formaPago;
/*  37:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  38:    */   @JoinColumn(name="id_secuencia", nullable=true)
/*  39:    */   private Secuencia secuencia;
/*  40:    */   @Column(name="indicador_cliente", nullable=true)
/*  41:    */   private boolean indicadorCliente;
/*  42:    */   @Column(name="indicador_proveedor", nullable=true)
/*  43:    */   private boolean indicadorProveedor;
/*  44:    */   
/*  45:    */   public int getId()
/*  46:    */   {
/*  47:101 */     return this.idFormaPagoCuentaBancariaOrganizacion;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getIdFormaPagoCuentaBancariaOrganizacion()
/*  51:    */   {
/*  52:110 */     return this.idFormaPagoCuentaBancariaOrganizacion;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setIdFormaPagoCuentaBancariaOrganizacion(int idFormaPagoCuentaBancariaOrganizacion)
/*  56:    */   {
/*  57:121 */     this.idFormaPagoCuentaBancariaOrganizacion = idFormaPagoCuentaBancariaOrganizacion;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getIdOrganizacion()
/*  61:    */   {
/*  62:130 */     return this.idOrganizacion;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIdOrganizacion(int idOrganizacion)
/*  66:    */   {
/*  67:140 */     this.idOrganizacion = idOrganizacion;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdSucursal()
/*  71:    */   {
/*  72:149 */     return this.idSucursal;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdSucursal(int idSucursal)
/*  76:    */   {
/*  77:159 */     this.idSucursal = idSucursal;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public FormaPago getFormaPago()
/*  81:    */   {
/*  82:168 */     return this.formaPago;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setFormaPago(FormaPago formaPago)
/*  86:    */   {
/*  87:178 */     this.formaPago = formaPago;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/*  91:    */   {
/*  92:187 */     return this.cuentaBancariaOrganizacion;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/*  96:    */   {
/*  97:197 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean isIndicadorDebitoBancario()
/* 101:    */   {
/* 102:201 */     return this.indicadorDebitoBancario;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setIndicadorDebitoBancario(boolean indicadorDebitoBancario)
/* 106:    */   {
/* 107:205 */     this.indicadorDebitoBancario = indicadorDebitoBancario;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Secuencia getSecuencia()
/* 111:    */   {
/* 112:214 */     return this.secuencia;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setSecuencia(Secuencia secuencia)
/* 116:    */   {
/* 117:224 */     this.secuencia = secuencia;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public boolean isIndicadorCliente()
/* 121:    */   {
/* 122:228 */     return this.indicadorCliente;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setIndicadorCliente(boolean indicadorCliente)
/* 126:    */   {
/* 127:232 */     this.indicadorCliente = indicadorCliente;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public boolean isIndicadorProveedor()
/* 131:    */   {
/* 132:236 */     return this.indicadorProveedor;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setIndicadorProveedor(boolean indicadorProveedor)
/* 136:    */   {
/* 137:240 */     this.indicadorProveedor = indicadorProveedor;
/* 138:    */   }
/* 139:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.FormaPagoCuentaBancariaOrganizacion
 * JD-Core Version:    0.7.0.1
 */
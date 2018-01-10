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
/*  13:    */ import javax.validation.constraints.NotNull;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="recargo_lista_precios_cliente", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_empresa", "id_lista_precios"})})
/*  17:    */ public class RecargoListaPreciosCliente
/*  18:    */   extends EntidadBase
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = -4318372801168600812L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="recargo_lista_precios_cliente", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="recargo_lista_precios_cliente")
/*  24:    */   @Column(name="id_recargo_lista_precios_cliente", unique=true, nullable=false)
/*  25:    */   private int idRecargoListaPreciosCliente;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   @Column(name="id_sucursal", nullable=false)
/*  29:    */   private int idSucursal;
/*  30:    */   @NotNull
/*  31:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  32:    */   @JoinColumn(name="id_lista_precios")
/*  33:    */   private ListaPrecios listaPrecios;
/*  34:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  35:    */   @JoinColumn(name="id_empresa", nullable=true)
/*  36:    */   private Empresa empresa;
/*  37:    */   @NotNull
/*  38:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  39:    */   @JoinColumn(name="id_cuenta_contable")
/*  40:    */   private CuentaContable cuentaContable;
/*  41:    */   
/*  42:    */   public int getId()
/*  43:    */   {
/*  44: 72 */     return this.idRecargoListaPreciosCliente;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public RecargoListaPreciosCliente() {}
/*  48:    */   
/*  49:    */   public RecargoListaPreciosCliente(Empresa empresa)
/*  50:    */   {
/*  51: 87 */     this.empresa = empresa;
/*  52: 88 */     this.idOrganizacion = empresa.getIdOrganizacion();
/*  53: 89 */     this.idSucursal = empresa.getIdSucursal();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getIdRecargoListaPreciosCliente()
/*  57:    */   {
/*  58: 93 */     return this.idRecargoListaPreciosCliente;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdRecargoListaPreciosCliente(int idRecargoListaPreciosCliente)
/*  62:    */   {
/*  63: 97 */     this.idRecargoListaPreciosCliente = idRecargoListaPreciosCliente;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public ListaPrecios getListaPrecios()
/*  67:    */   {
/*  68:101 */     return this.listaPrecios;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setListaPrecios(ListaPrecios listaPrecios)
/*  72:    */   {
/*  73:105 */     this.listaPrecios = listaPrecios;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Empresa getEmpresa()
/*  77:    */   {
/*  78:109 */     return this.empresa;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setEmpresa(Empresa empresa)
/*  82:    */   {
/*  83:113 */     this.empresa = empresa;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public CuentaContable getCuentaContable()
/*  87:    */   {
/*  88:117 */     return this.cuentaContable;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setCuentaContable(CuentaContable cuentaContable)
/*  92:    */   {
/*  93:121 */     this.cuentaContable = cuentaContable;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int getIdOrganizacion()
/*  97:    */   {
/*  98:125 */     return this.idOrganizacion;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setIdOrganizacion(int idOrganizacion)
/* 102:    */   {
/* 103:129 */     this.idOrganizacion = idOrganizacion;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int getIdSucursal()
/* 107:    */   {
/* 108:133 */     return this.idSucursal;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setIdSucursal(int idSucursal)
/* 112:    */   {
/* 113:137 */     this.idSucursal = idSucursal;
/* 114:    */   }
/* 115:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.RecargoListaPreciosCliente
 * JD-Core Version:    0.7.0.1
 */
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
/*  14:    */ import javax.validation.constraints.Size;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="empresa_atributo")
/*  18:    */ public class EmpresaAtributo
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="empresa_atributo", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="empresa_atributo")
/*  25:    */   @Column(name="id_empresa_atributo", unique=true, nullable=false)
/*  26:    */   private int idEmpresaAtributo;
/*  27:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  28:    */   @JoinColumn(name="id_atributo", nullable=false)
/*  29:    */   @NotNull
/*  30:    */   private Atributo atributo;
/*  31:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  32:    */   @JoinColumn(name="id_conjunto_atributo", nullable=false)
/*  33:    */   @NotNull
/*  34:    */   private ConjuntoAtributo conjuntoAtributo;
/*  35:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  36:    */   @JoinColumn(name="id_empresa", nullable=true)
/*  37:    */   private Empresa empresa;
/*  38:    */   @Column(name="id_organizacion", nullable=false)
/*  39:    */   @NotNull
/*  40:    */   private int idOrganizacion;
/*  41:    */   @Column(name="id_sucursal", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   private int idSucursal;
/*  44:    */   @Column(name="valor", length=50)
/*  45:    */   @Size(max=50)
/*  46: 56 */   private String valor = "";
/*  47:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  48:    */   @JoinColumn(name="id_valor_atributo", nullable=true)
/*  49:    */   private ValorAtributo valorAtributo;
/*  50:    */   @Column(name="indicador_proveedor")
/*  51:    */   private boolean indicadorProveedor;
/*  52:    */   @Column(name="indicador_cliente")
/*  53:    */   private boolean indicadorCliente;
/*  54:    */   
/*  55:    */   public EmpresaAtributo() {}
/*  56:    */   
/*  57:    */   public EmpresaAtributo(Empresa empresa, Atributo atributo)
/*  58:    */   {
/*  59: 74 */     this.empresa = empresa;
/*  60: 75 */     this.atributo = atributo;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getId()
/*  64:    */   {
/*  65: 80 */     return this.idEmpresaAtributo;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdEmpresaAtributo()
/*  69:    */   {
/*  70: 87 */     return this.idEmpresaAtributo;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdEmpresaAtributo(int idEmpresaAtributo)
/*  74:    */   {
/*  75: 95 */     this.idEmpresaAtributo = idEmpresaAtributo;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Atributo getAtributo()
/*  79:    */   {
/*  80:102 */     return this.atributo;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setAtributo(Atributo atributo)
/*  84:    */   {
/*  85:110 */     this.atributo = atributo;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public ConjuntoAtributo getConjuntoAtributo()
/*  89:    */   {
/*  90:117 */     return this.conjuntoAtributo;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setConjuntoAtributo(ConjuntoAtributo conjuntoAtributo)
/*  94:    */   {
/*  95:125 */     this.conjuntoAtributo = conjuntoAtributo;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Empresa getEmpresa()
/*  99:    */   {
/* 100:132 */     return this.empresa;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setEmpresa(Empresa empresa)
/* 104:    */   {
/* 105:140 */     this.empresa = empresa;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int getIdOrganizacion()
/* 109:    */   {
/* 110:147 */     return this.idOrganizacion;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setIdOrganizacion(int idOrganizacion)
/* 114:    */   {
/* 115:155 */     this.idOrganizacion = idOrganizacion;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public int getIdSucursal()
/* 119:    */   {
/* 120:162 */     return this.idSucursal;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setIdSucursal(int idSucursal)
/* 124:    */   {
/* 125:170 */     this.idSucursal = idSucursal;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String getValor()
/* 129:    */   {
/* 130:177 */     return this.valor;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setValor(String valor)
/* 134:    */   {
/* 135:185 */     this.valor = valor;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public ValorAtributo getValorAtributo()
/* 139:    */   {
/* 140:192 */     return this.valorAtributo;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setValorAtributo(ValorAtributo valorAtributo)
/* 144:    */   {
/* 145:200 */     this.valorAtributo = valorAtributo;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean isIndicadorProveedor()
/* 149:    */   {
/* 150:207 */     return this.indicadorProveedor;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setIndicadorProveedor(boolean indicadorProveedor)
/* 154:    */   {
/* 155:215 */     this.indicadorProveedor = indicadorProveedor;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public boolean isIndicadorCliente()
/* 159:    */   {
/* 160:222 */     return this.indicadorCliente;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setIndicadorCliente(boolean indicadorCliente)
/* 164:    */   {
/* 165:230 */     this.indicadorCliente = indicadorCliente;
/* 166:    */   }
/* 167:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.EmpresaAtributo
 * JD-Core Version:    0.7.0.1
 */
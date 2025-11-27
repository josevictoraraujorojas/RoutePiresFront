package com.example.routepiresfront.chat

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.FragmentChatBinding
import com.example.routepiresfront.ui.chat.ChatAdapter
import com.example.routepiresfront.ui.chat.ChatViewModel
import com.example.routepiresfront.viewmodel.ChatViewModelFactory
import br.gov.ifgoiano.routepires.data.remote.ChatService
import com.example.routepiresfront.data.remote.MensagemService
import br.gov.ifgoiano.routepiresfront.repository.ChatRepository
import br.gov.ifgoiano.routepiresfront.repository.MensagemRepository
import com.example.routepiresfront.data.remote.ApiClient
import com.google.android.material.bottomnavigation.BottomNavigationView

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    // Safe args
    private val args: ChatFragmentArgs by navArgs()

    // ViewModel (created with factory below)
    private val viewModel: ChatViewModel by viewModels {
        // cria services via seu ApiClient
        val chatApi = ApiClient.getService(ChatService::class.java)
        val mensagemApi = ApiClient.getService(MensagemService::class.java)

        // repositórios
        val chatRepository = ChatRepository(chatApi)
        val mensagemRepository = MensagemRepository(mensagemApi)

        ChatViewModelFactory(chatRepository, mensagemRepository)
    }

    // Adapter (usa userId depois de inicializar)
    private lateinit var adapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        // vincula viewModel ao data binding (xml tem a variável viewModel)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // pega negociacao enviada pelo Safe Args
        val negociacao = args.negociacao
        val chatId = negociacao.chat.id
        val usuarioLogado = negociacao.usarioLogado

        // destinatario (outro participante)
        val destinatarioId = negociacao.chat.participantes?.firstOrNull { it != usuarioLogado } ?: ""

        // configura cabeçalho
        binding.chatToolbar.textViewName.text = negociacao.nome
        // se você tiver nota no chat, ajuste; aqui deixo defaults
        binding.chatToolbar.ratingBar.rating = 0f
        binding.chatToolbar.textViewRatingValue.text = ""

        binding.chatToolbar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.chatToolbar.buttonReport.setOnClickListener {
            mostrarPopupDenuncia()
        }

        // cria adapter com id do usuário logado
        adapter = ChatAdapter(usuarioLogado)

        // configura RecyclerView
        val layoutManager = LinearLayoutManager(requireContext()).apply {
            // respeitar reverseLayout definido no xml: se quiser forçar, altere aqui
            reverseLayout = true
        }
        binding.recyclerViewChat.layoutManager = layoutManager
        binding.recyclerViewChat.adapter = adapter

        // inicializa ViewModel (carrega mensagens e marca lidas)
        if (chatId != null) {
            viewModel.inicializar(chatId, usuarioLogado)
        } else {
            // caso chatId nulo, você pode criar chat ou mostrar erro
            // por enquanto apenas retorna
            return
        }

        // observa mensagens e atualiza adapter
        viewModel.mensagens.observe(viewLifecycleOwner) { lista ->
            adapter.atualizarMensagens(lista)

            // scroll: se reverseLayout == true, o "fim" está em position 0
            val lm = binding.recyclerViewChat.layoutManager
            val pos = if (lm is LinearLayoutManager && lm.reverseLayout) 0 else (lista.size - 1).coerceAtLeast(0)
            if (pos >= 0) {
                binding.recyclerViewChat.scrollToPosition(pos)
            }
        }

        // erros
        viewModel.erro.observe(viewLifecycleOwner) { err ->
            err?.let {
                // opcional: mostrar Toast/snackbar
            }
        }

        // botão recusar
        binding.buttonRecusarCorrida.setOnClickListener {
            findNavController().popBackStack()
        }

        // botão aceitar: exemplo já do seu app
        binding.buttonAceitarCorrida.setOnClickListener {
            val bottom = requireActivity().findViewById<BottomNavigationView>(R.id.menuInferior)
            bottom.selectedItemId = R.id.bottom_home

            val corridaNav = requireActivity()
                .supportFragmentManager
                .findFragmentById(R.id.nav_host_home_moto)
                ?.findNavController()

            corridaNav?.navigate(R.id.action_global_aguardandoInicioCorridaFragment2)
        }

        // enviar mensagem (usa destinatarioId calculado)
        binding.buttonEnviar.setOnClickListener {
            val texto = binding.editTextMensagem.text.toString().trim()
            if (texto.isNotEmpty()) {
                viewModel.enviarMensagem(texto, destinatarioId)
                binding.editTextMensagem.text.clear()
            }
        }
    }

    private fun mostrarPopupDenuncia() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_denuncia, null)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialogView.findViewById<Button>(R.id.btnCancelar).setOnClickListener {
            dialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.btnConfirmar).setOnClickListener {
            val usuarioLogado = args.negociacao.usarioLogado
            val usuarioDenunciado = args.negociacao.chat.participantes?.firstOrNull { it != usuarioLogado } ?: ""

            val action = ChatFragmentDirections.actionChatParaDenuncia(
                usuarioLogadoId = usuarioLogado,
                usuarioDenunciadoId = usuarioDenunciado
            )
            findNavController().navigate(action)
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
